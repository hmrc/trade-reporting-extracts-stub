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

import play.api.libs.json.Json
import play.api.mvc.{Action, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import uk.gov.hmrc.tradereportingextractsstub.models.EoriRequest
import uk.gov.hmrc.tradereportingextractsstub.services.EoriHistoryService

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton()
class EoriHistoryController @Inject() (eoriHistoryService: EoriHistoryService, cc: ControllerComponents)
    extends BackendController(cc):

  def eoriHistory(): Action[EoriRequest] = Action.async(parse.json[EoriRequest]) { implicit request =>
    val eori        = request.body.eori
    val eoriHistory = eoriHistoryService.eoriHistory(eori)
    Future.successful(Ok(Json.toJson(eoriHistory)))
  }
