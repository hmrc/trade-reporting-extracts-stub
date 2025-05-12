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

import play.api.mvc.{Action, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import uk.gov.hmrc.tradereportingextractsstub.services.EpidReportService
import play.api.libs.json.{JsValue, Json, Writes}
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.http.HeaderCarrierConverter

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class EpidReportController @Inject() (implicit
  ec: ExecutionContext,
  epidReportService: EpidReportService,
  cc: ControllerComponents
) extends BackendController(cc) {

  def handleRequest(): Action[JsValue] = Action.async(parse.json) { request =>
    implicit val headerCarrier: HeaderCarrier = HeaderCarrierConverter.fromRequestAndSession(request, request.session)
    epidReportService.isJsonValid(request.body, headerCarrier).map {
      case Right(_)                   =>
        Accepted(Json.toJson("Accepted"))
      case Left(errorMessage: String) =>
        BadRequest(Json.toJson(Map("error" -> errorMessage)))
    }
  }
}
