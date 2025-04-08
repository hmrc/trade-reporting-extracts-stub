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

package uk.gov.hmrc.tradereportingextractsstub.controllers.epid

import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import uk.gov.hmrc.tradereportingextractsstub.services.{DateTimeService, SchemaValidator, UuidIdService, ValidationService}
import javax.inject.{Inject, Singleton}

@Singleton
class EpidReportController @Inject() (
                                       schemaValidator: SchemaValidator,
                                       override val controllerComponents: ControllerComponents
                                     ) extends BackendController(controllerComponents) {

  def handleRequest(): Action[JsValue] = Action(parse.json) { request =>

    schemaValidator.isJsonValid(request.body) match {
      case Right(_) =>
        Accepted(Json.toJson("Accepted"))
      case Left(errorMessage) =>
        BadRequest(Json.toJson(errorMessage))
    }
  }
}