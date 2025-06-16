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
import play.api.http.Status.OK
import play.api.libs.json.Json
import play.api.test.Helpers.{POST, contentAsJson, route, status}
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.tradereportingextractsstub.models.{AddressInformation, CompanyInformation, EoriRequest}
import uk.gov.hmrc.tradereportingextractsstub.utils.SpecBase

class CompanyInformationControllerSpec extends SpecBase {

  "GET /company-information" should {
    "return 200" in new Setup {

      val eoriRequest = EoriRequest(eori = "GB123456789012")
      val request     = FakeRequest(POST, routes.CompanyInformationController.companyInformation().url)
        .withBody(Json.toJson(eoriRequest))
      val result      = route(app, request).value
      status(result) shouldBe OK
      val expectedObj =
        CompanyInformation("ABC Company", "1", AddressInformation("XYZ Street", "ABC City", Some("G11 2ZZ"), "GB"))
      val actualObj   = contentAsJson(result).as[CompanyInformation]
      actualObj shouldBe expectedObj
    }
  }

  trait Setup {
    val app: Application = application.build()
  }
}
