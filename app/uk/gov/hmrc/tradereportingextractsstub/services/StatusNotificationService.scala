/*
 * Copyright 2025 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.tradereportingextractsstub.services

import uk.gov.hmrc.tradereportingextractsstub.connectors.TREConnector
import uk.gov.hmrc.tradereportingextractsstub.models.StatusNotification
import uk.gov.hmrc.tradereportingextractsstub.utils.ApplicationConstants.*

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import org.apache.pekko.actor.ActorSystem
import play.api.Logging
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.tradereportingextractsstub.config.AppConfig
import scala.concurrent.duration.*
import scala.concurrent.ExecutionContext

class StatusNotificationService @Inject() (implicit
  ec: ExecutionContext,
  treConnector: TREConnector,
  system: ActorSystem,
  appConfig: AppConfig
) extends Logging {

  def scheduleStatusUpdate(hc: HeaderCarrier): Unit =
    StatusNotification.allNotifications.zipWithIndex.foreach { case (notification, index) =>
      val notificationDelay: FiniteDuration = appConfig.statusNotificationDelay.minutes * index
      system.scheduler.scheduleOnce(notificationDelay) {
        try handleRequest(notification)(hc)
        catch {
          case e: Exception => logger.error("Error scheduling status update", e)
        }
      }
    }

  def handleRequest(notification: StatusNotification)(implicit hc: HeaderCarrier): Unit = {
    val correlationId: Option[String] = getCorrelationIDKey(hc)
    val headers                       = getHeader(notification, correlationId)
    val body                          = getBody(notification)
    logger.info("Sending status update with headers: " + notification.toString)
    treConnector.sendStatusUpdate(headers, body)
  }

  private def getBody(notification: StatusNotification) =
    Map(
      applicationComponent -> notification.component,
      statusType           -> notification.notificationType,
      statusCode           -> notification.code,
      statusMessage        -> notification.description,
      statusTimestamp      -> notification.statusTimestamp
    )

  private def getHeader(notification: StatusNotification, correlationId: Option[String]) =
    Seq(
      CorrelationIdKey    -> correlationId.getOrElse(""),
      SourceSystem        -> notification.component,
      XTransmittingSystem -> IF,
      Date                -> LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS")),
      ContentType         -> contentJson
    )

  private def getCorrelationIDKey(hc: HeaderCarrier) = {
    val correlationId = hc.otherHeaders
      .collectFirst { case (key, value) if key.equalsIgnoreCase(CorrelationIdKey) => value }
    correlationId
  }
}
