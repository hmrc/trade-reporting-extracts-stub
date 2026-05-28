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
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import uk.gov.hmrc.tradereportingextractsstub.models.EoriRequest
import uk.gov.hmrc.tradereportingextractsstub.services.EoriHistoryService

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton()
class EoriHistoryController @Inject() (eoriHistoryService: EoriHistoryService, cc: ControllerComponents)
    extends BackendController(cc):

  // POST calls for third party to get EORI history of trader
  def eoriHistory(): Action[EoriRequest] = Action.async(parse.json[EoriRequest]) { implicit request =>
    val eori        = request.body.eori
    val eoriHistory = eoriHistoryService.eoriHistory(eori, false)
    Future.successful(Ok(Json.toJson(eoriHistory)))
  }

  def eoriHistoryStrategic(): Action[EoriRequest] = Action.async(parse.json[EoriRequest]) { implicit request =>
    val eori        = request.body.eori
    val xiRequired  = if (eori.startsWith("GB9")) true else false
    val eoriHistory = eoriHistoryService.eoriHistory(eori, xiRequired)
    Future.successful(Ok(Json.toJson(eoriHistory)))
  }

  // GET calls for trader to get their own EORI history
  def eoriTraderHistory(): Action[AnyContent] = Action.async { implicit request =>
    val eoriHistory = eoriHistoryService.eoriHistory("GB123456789000", false)
    Future.successful(Ok(Json.toJson(eoriHistory)))
  }

  def eoriTraderHistoryStrategic(): Action[AnyContent] = Action.async { implicit request =>
    val eoriHistory = eoriHistoryService.eoriHistory("GB123456789000", true)
    Future.successful(Ok(Json.toJson(eoriHistory)))
  }
