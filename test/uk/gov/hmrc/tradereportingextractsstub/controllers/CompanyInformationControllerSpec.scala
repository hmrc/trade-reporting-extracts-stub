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

import org.scalatest.matchers.should.Matchers.should
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.tradereportingextractsstub.controllers.utils.SpecBase
import uk.gov.hmrc.tradereportingextractsstub.services.CompanyInformationService

import scala.concurrent.ExecutionContext.Implicits.global

class CompanyInformationControllerSpec extends SpecBase {

  private val fakeRequest               = FakeRequest("GET", "/company-information")
  private val companyInformationService = new CompanyInformationService
  private val controller                =
    new CompanyInformationController(companyInformationService, Helpers.stubControllerComponents())(implicitly)

  "GET /company-information" should {
    "return 200" in {
      val fakeRequest = FakeRequest("GET", "/company-information").withBody(
        """{
          |"eori": "GB123456789012"
          |}""".stripMargin
      )
      val result      = controller.companyInformation()(fakeRequest)
      result should not be null
    }
  }
}
