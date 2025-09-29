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

package uk.gov.hmrc.tradereportingextractsstub.controllers

import play.api.libs.json.*
import play.api.mvc.*
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.tradereportingextractsstub.config.AppConfig
import uk.gov.hmrc.tradereportingextractsstub.models.AllowedEoris
import uk.gov.hmrc.tradereportingextractsstub.models.sdes.FilesAvailableHeaders.*
import uk.gov.hmrc.tradereportingextractsstub.models.sdes.{FileAvailableStubRequest, FilesAvailableHeaders}
import uk.gov.hmrc.tradereportingextractsstub.services.FileAvailableService

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source

@Singleton
class FilesAvailableController @Inject() (
  cc: ControllerComponents,
  fa: FileAvailableService,
  appConfig: AppConfig
) (implicit ec: ExecutionContext) extends AbstractController(cc)
    with AllowedEoris {

  def filesAvailable(informationType: String): Action[AnyContent] = Action.async { request =>
    val xClientId: String = request.headers.get(XClientId.toString).getOrElse("")
    val eori: String = request.headers.get(XSdesKey.toString).getOrElse("")
      eori match {
        case _ if xClientId.isEmpty => Future.successful(BadRequest("Missing x-client-id header"))
        case _ if xClientId != appConfig.treXClientId => Future.successful(Forbidden("Invalid x-client-id header"))
        case _ if informationType.isEmpty => Future.successful(BadRequest("Missing information type"))
        case _ if informationType != appConfig.treInformationType =>
          Future.successful(Forbidden("Invalid information type"))
        case _ if eori.isEmpty => Future.successful(BadRequest("Missing x-sdes-key header"))
        case _ if !allowedEoris.contains(eori) => Future.successful(Forbidden("x-sdes-key/EORI not allowed"))
        case _ => generateFilesAvailableJson(eori).map(json => Ok(json))
      }
  }

  private def generateFilesAvailableJson(
                                          eori: String
                                        ): Future[JsValue] = {
    val template = Source.fromResource("resources/FilesAvailableResponse.json").mkString
    fa.getAvailableReports(eori)(HeaderCarrier()).map { reports =>
      if (reports.isEmpty) {
        Json.parse("")
      } else {
        val jsonStrings = reports.map { r =>
          template
            .replace("{{EORI_VALUE}}", eori)
            .replace("{{COR_ID}}", r.correlationId)
            .replace("{{REP_ID}}", r.reportRequestId)
            .replace("{{REP_TYP}}", r.reportTypeName.toString)
        }
        Json.parse(s"[${jsonStrings.mkString(",")}]")
      }
    }
  }
}
