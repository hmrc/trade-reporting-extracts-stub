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

import play.api.libs.json
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json, JsonValidationError}
import play.api.mvc.{Action, AnyContent, ControllerComponents, Request}
import sttp.model.MediaType.ApplicationJson
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import uk.gov.hmrc.play.http.HeaderCarrierConverter
import uk.gov.hmrc.tradereportingextractsstub.config.AppConfig
import uk.gov.hmrc.tradereportingextractsstub.models.eis.*
import uk.gov.hmrc.tradereportingextractsstub.models.eis.EisReportRequestHeaders.*
import uk.gov.hmrc.tradereportingextractsstub.services.EisReportService
import uk.gov.hmrc.tradereportingextractsstub.utils.HttpDateFormatter.getCurrentHttpDate

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class EisReportController @Inject() (
  eisReportService: EisReportService,
  cc: ControllerComponents,
  appConfig: AppConfig
)(using ec: ExecutionContext)
    extends BackendController(cc) {

  def requestTraderReport(): Action[JsValue] = Action.async(parse.json) { request =>
    implicit val hc: HeaderCarrier = HeaderCarrierConverter.fromRequest(request)
    val missingHeaders             =
      EisReportRequestHeaders.allHeaders.filterNot(header => request.headers.get(header).isDefined)
    if missingHeaders.nonEmpty then {
      Future.successful(
        BadRequest(buildBadRequestHeaderResponse(request, missingHeaders)).withHeaders(
          ContentType.toString    -> ApplicationJson.toString(),
          Date.toString           -> getCurrentHttpDate,
          XCorrelationID.toString -> request.headers.get(XCorrelationID.toString).getOrElse("")
        )
      )
    } else if !request.headers.get(Authorization.toString).getOrElse("").equals(appConfig.eisAuthToken) then {
      Future.successful(
        Forbidden.withHeaders(
          Date.toString           -> getCurrentHttpDate,
          XCorrelationID.toString -> request.headers.get(XCorrelationID.toString).getOrElse("")
        )
      )
    } else
      request.body.validate[EisReportRequest] match {
        case JsError(errors) =>
          val errorMessage = errors
            .map { case (path, validationErrors) =>
              s"Invalid value at path $path: ${validationErrors.map(_.message).mkString(", ")}"
            }
            .mkString(", ")
          Future(
            BadRequest(buildBadRequestBodyResponse(request, List(errorMessage))).withHeaders(
              ContentType.toString    -> ApplicationJson.toString(),
              Date.toString           -> getCurrentHttpDate,
              XCorrelationID.toString -> request.headers.get(XCorrelationID.toString).getOrElse("")
            )
          )
        case JsSuccess(_, _) =>
          Future(
            NoContent.withHeaders(
              Date.toString           -> getCurrentHttpDate,
              XCorrelationID.toString -> request.headers.get(XCorrelationID.toString).getOrElse("")
            )
          )
      }
  }

  private def buildBadRequestBodyResponse(request: Request[JsValue], errors: List[String])           =
    Json.toJson(
      EisReportResponseError(
        errorDetail = EisReportResponseErrorDetail(
          correlationId = request.headers.get(XCorrelationID.toString).getOrElse(""),
          errorCode = Some("400"),
          errorMessage = Some("Failed body validation"),
          source = Some("EIS"),
          sourceFaultDetail = Some(
            EisReportResponseErrorDetailSourceFaultDetail(
              detail = errors.map(error => s"Failed body validation: Invalid $error"),
              restFault = None,
              soapFault = None
            )
          ),
          timestamp = getCurrentHttpDate
        )
      )
    )
  private def buildBadRequestHeaderResponse(request: Request[JsValue], missingHeaders: List[String]) =
    Json.toJson(
      EisReportResponseError(
        errorDetail = EisReportResponseErrorDetail(
          correlationId = request.headers.get(XCorrelationID.toString).getOrElse(""),
          errorCode = Some("400"),
          errorMessage = Some("Failed header validation"),
          source = Some("EIS"),
          sourceFaultDetail = Some(
            EisReportResponseErrorDetailSourceFaultDetail(
              detail = missingHeaders.map(header => s"Failed header validation: Invalid $header header"),
              restFault = None,
              soapFault = None
            )
          ),
          timestamp = getCurrentHttpDate
        )
      )
    )

  def serverOtherMethods(): Action[AnyContent] = Action.async { request =>
    Future.successful(
      MethodNotAllowed.withHeaders(
        Date.toString           -> getCurrentHttpDate,
        XCorrelationID.toString -> request.headers.get(XCorrelationID.toString).getOrElse("")
      )
    )
  }

  def serverOthers(): Action[JsValue] = Action.async(parse.json) { request =>
    Future.successful(
      NotFound.withHeaders(
        Date.toString           -> getCurrentHttpDate,
        XCorrelationID.toString -> request.headers.get(XCorrelationID.toString).getOrElse("")
      )
    )
  }
}
