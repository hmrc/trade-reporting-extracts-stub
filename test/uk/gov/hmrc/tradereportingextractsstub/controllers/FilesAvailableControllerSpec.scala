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
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.Helpers.{GET, route, running, status, writeableOf_AnyContentAsEmpty, writeableOf_AnyContentAsJson}
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.tradereportingextractsstub.models.sdes.FileAvailableStubRequest
import uk.gov.hmrc.tradereportingextractsstub.utils.SpecBase

class FilesAvailableControllerSpec extends SpecBase {

  "GET /files-available/list/GB123456789012" should {
    "return 200" in new Setup {
      running(app) {

        val stubRequest  = FileAvailableStubRequest(
          reportRequestId = "REPORT_ID_123",
          fileParts = 2
        )
        val stubRequests = Seq(stubRequest)
        val jsonBody     = Json.toJson(stubRequests)

        val request = FakeRequest(GET, routes.FilesAvailableController.filesAvailable("TRE").url)
          .withHeaders("x-client-id" -> "TRE-CLIENT-ID", "x-sdes-key" -> "GB123456789012")
          .withJsonBody(jsonBody)
        val result  = route(app, request).value
        status(result) shouldBe Status.OK
      }
    }
  }
  "GET /files-available/list/GB123456789012" should {
    "return 403 invalid client id" in new Setup {
      running(app) {
        val request = FakeRequest(GET, routes.FilesAvailableController.filesAvailable("TRE").url)
          .withHeaders("x-client-id" -> "Invalid-Client-id", "x-sdes-key" -> "GB123456789012")
        val result  = route(app, request).value
        status(result) shouldBe Status.FORBIDDEN
      }
    }
  }
  "GET /files-available/list/GB123456789012" should {
    "return 403 invalid eori" in new Setup {
      running(app) {
        val request = FakeRequest(GET, routes.FilesAvailableController.filesAvailable("TRE").url)
          .withHeaders("x-client-id" -> "TRE-CLIENT-ID", "x-sdes-key" -> "Invalid-Sdes-Key")
        val result  = route(app, request).value
        status(result) shouldBe Status.FORBIDDEN
      }
    }
  }
  "GET /files-available/list/GB123456789012" should {
    "return 403 invalid information type" in new Setup {
      running(app) {
        val request = FakeRequest(GET, routes.FilesAvailableController.filesAvailable("Invalid-information-type").url)
          .withHeaders("x-client-id" -> "TRE-CLIENT-ID", "x-sdes-key" -> "GB123456789012")
        val result  = route(app, request).value
        status(result) shouldBe Status.FORBIDDEN
      }
    }
  }

  trait Setup {
    val app: Application = application.build()
  }
}
