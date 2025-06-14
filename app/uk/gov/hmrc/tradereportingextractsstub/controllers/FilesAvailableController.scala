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
import uk.gov.hmrc.tradereportingextractsstub.config.AppConfig
import uk.gov.hmrc.tradereportingextractsstub.models.AllowedEoris
import uk.gov.hmrc.tradereportingextractsstub.models.sdes.FilesAvailableHeaders.*
import uk.gov.hmrc.tradereportingextractsstub.models.sdes.{FileAvailableStubRequest, FilesAvailableHeaders}
import uk.gov.hmrc.tradereportingextractsstub.utils.StubResource

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future
import scala.io.Source

@Singleton
class FilesAvailableController @Inject() (
  cc: ControllerComponents,
  appConfig: AppConfig
) extends AbstractController(cc)
    with AllowedEoris
    with StubResource {

  def filesAvailable(informationType: String): Action[AnyContent] = Action.async { request =>
    val xClientId: String = request.headers.get(XClientId.toString).getOrElse("")
    val eori: String      = request.headers.get(XSdesKey.toString).getOrElse("")

    eori match {
      case _ if xClientId.isEmpty                               => Future.successful(BadRequest("Missing x-client-id header"))
      case _ if xClientId != appConfig.treXClientId             => Future.successful(Forbidden("Invalid x-client-id header"))
      case _ if informationType.isEmpty                         => Future.successful(BadRequest("Missing information type"))
      case _ if informationType != appConfig.treInformationType =>
        Future.successful(Forbidden("Invalid information type"))
      case _ if eori.isEmpty                                    => Future.successful(BadRequest("Missing x-sdes-key header"))
      case _ if !allowedEoris.contains(eori)                    => Future.successful(Forbidden("x-sdes-key/EORI not allowed"))
      case _                                                    =>
        request.body.asJson.flatMap(_.asOpt[Seq[FileAvailableStubRequest]]) match {
          case Some(requests) if requests.nonEmpty =>
            val responseJson = generateFilesAvailableJson(eori, requests)
            Future.successful(Ok(responseJson))
          case _                                   =>
            Future.successful(BadRequest("Invalid or missing requests in JSON body"))
        }
      // Future.successful(jsonResourceAsResponse("resources/FilesAvailableResponse.json", eori))
    }
  }

  private def generateFilesAvailableJson(
    eori: String,
    requests: Seq[FileAvailableStubRequest]
  ): JsValue = {
    val template = Source.fromResource("resources/FilesAvailableResponse.json").mkString

    val allParts = requests.flatMap { req =>
      (1 to req.fileParts).map { partNum =>
        val replaced = template
          .replace("{{EORI_VALUE}}", eori)
          .replace("{{REPORT_ID}}", req.reportRequestId)
          .replace("{{PART_NUM}}", partNum.toString)
          .replace("{{TOTAL_PARTS}}", req.fileParts.toString)
        Json.parse(replaced)
      }
    }
    JsArray(allParts)
  }
}
