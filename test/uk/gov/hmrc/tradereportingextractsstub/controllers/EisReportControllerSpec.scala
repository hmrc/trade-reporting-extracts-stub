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

import org.scalatest.matchers.should.Matchers.{should, shouldBe}
import play.api.Application
import play.api.http.Status.BAD_REQUEST
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers.*
import uk.gov.hmrc.tradereportingextractsstub.utils.SpecBase

class EisReportControllerSpec extends SpecBase {

  "EisReportControllerSpec" should {
    "return 200 OK" in new Setup {
      val request = FakeRequest(PUT, routes.EisReportController.requestTraderReport().url)
        .withHeaders(
          "accept"           -> "application/json",
          "authorization"    -> "Bearer EisAuthToken",
          "content-type"     -> "application/json",
          "date"             -> "Mon, 02 Oct 2023 14:30:00 GMT",
          "x-correlation-id" -> "46cd6ed6-21ba-4e05-975e-56d02011243c",
          "x-forwarded-host" -> "CDAP"
        )
        .withJsonBody(
          Json.obj(
            "endDate"          -> "2024-06-30T23:59:59Z",
            "eori"             -> Json.arr("GB123456789012", "GB123456789013"),
            "eoriRole"         -> "TRADER",
            "reportTypeName"   -> "IMPORTS-ITEM-REPORT",
            "requestID"        -> "RE57965342",
            "requestTimestamp" -> "2024-06-01T12:00:00Z",
            "requesterEori"    -> "GB123456789012",
            "startDate"        -> "2024-06-01T00:00:00Z"
          )
        )
      val result  = route(app, request).value
      status(result) shouldBe NO_CONTENT
    }
    "return 400 BadRequest with invalid header" in new Setup {
      val request = FakeRequest(PUT, routes.EisReportController.requestTraderReport().url)
        .withHeaders(
          "accept"           -> "application/json",
          "authorization"    -> "Bearer EisAuthToken",
          "content-type"     -> "application/json",
          "date"             -> "Mon, 02 Oct 2023 14:30:00 GMT",
          "x-correlation-id" -> "46cd6ed6-21ba-4e05-975e-56d02011243c"
        )
        .withJsonBody(
          Json.obj(
            "endDate"          -> "2024-06-30T23:59:59Z",
            "eori"             -> Json.arr("GB123456789012", "GB123456789013"),
            "eoriRole"         -> "TRADER",
            "reportTypeName"   -> "IMPORTS-ITEM-REPORT",
            "requestID"        -> "RE57965342",
            "requestTimestamp" -> "2024-06-01T12:00:00Z",
            "requesterEori"    -> "GB123456789012",
            "startDate"        -> "2024-06-01T00:00:00Z"
          )
        )
      val result  = route(app, request).value
      status(result)        shouldBe BAD_REQUEST
      contentAsString(result) should include("Failed header validation: Invalid x-forwarded-host header")
    }
    "return 400 BadRequest with empty body" in new Setup {
      val request = FakeRequest(PUT, routes.EisReportController.requestTraderReport().url)
        .withHeaders(
          "accept"           -> "application/json",
          "authorization"    -> "Bearer EisAuthToken",
          "content-type"     -> "application/json",
          "date"             -> "Mon, 02 Oct 2023 14:30:00 GMT",
          "x-correlation-id" -> "46cd6ed6-21ba-4e05-975e-56d02011243c",
          "x-forwarded-host" -> "CDAP"
        )
      val result  = route(app, request).value
      status(result) shouldBe BAD_REQUEST
    }
    "return 400 BadRequest with invalid JSON" in new Setup {
      val request = FakeRequest(PUT, routes.EisReportController.requestTraderReport().url)
        .withHeaders(
          "accept"           -> "application/json",
          "authorization"    -> "Bearer EisAuthToken",
          "content-type"     -> "application/json",
          "date"             -> "Mon, 02 Oct 2023 14:30:00 GMT",
          "x-correlation-id" -> "46cd6ed6-21ba-4e05-975e-56d02011243c",
          "x-forwarded-host" -> "CDAP"
        )
        .withJsonBody(
          Json.obj(
            "endDate"          -> "2024-06-30T23:59:59Z",
            "eori"             -> Json.arr("GB123456789012", "GB123456789013"),
            "eoriRole"         -> "TRADER",
            "reportTypeName"   -> "IMPORTS-ITEM-REPORT",
            "requestTimestamp" -> "2024-06-01T12:00:00Z",
            "requesterEori"    -> "GB123456789012",
            "startDate"        -> "2024-06-01T00:00:00Z"
          )
        )
      val result  = route(app, request).value
      status(result)        shouldBe BAD_REQUEST
      contentAsString(result) should include("Invalid value at path /requestID: error.path.missing")
    }
  }
  trait Setup {
    val app: Application = application.build()
  }
}
