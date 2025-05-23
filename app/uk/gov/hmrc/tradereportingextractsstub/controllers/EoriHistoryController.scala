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

import play.api.libs.json.JsValue
import play.api.mvc.{Action, AnyContent, ControllerComponents, Request}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import uk.gov.hmrc.tradereportingextractsstub.services.EoriHistoryService

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class EoriHistoryController @Inject() (eoriHistoryService: EoriHistoryService, cc: ControllerComponents)(implicit
  ec: ExecutionContext
) extends BackendController(cc):

  def eoriHistory(): Action[JsValue] = Action.async(parse.json) { implicit request: Request[JsValue] =>
    val jsonBody  = request.body
    val eoriValue = (jsonBody \ "eori").asOpt[String].getOrElse("defaultValue")
    Future(eoriHistoryService.eoriHistory(eoriValue))
  }
