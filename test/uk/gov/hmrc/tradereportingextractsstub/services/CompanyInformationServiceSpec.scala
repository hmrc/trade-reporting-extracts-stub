/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.tradereportingextractsstub.services

import org.scalatest.freespec.AnyFreeSpec
import uk.gov.hmrc.tradereportingextractsstub.models.{AddressInformation, CompanyInformation}

class CompanyInformationServiceSpec extends AnyFreeSpec {

  "CompanyInformationService" - {

    val companyInfo =
      CompanyInformation(
        "foo",
        "0",
        AddressInformation(
          "XYZ Street",
          "ABC City",
          Some("G11 2ZZ"),
          "GB"
        )
      )

    "should return no consent for EORI starting with GB99" in {
      val service     = new CompanyInformationService
      val result      = service.companyInformation("GB991234567890")
      val returnValue = companyInfo.copy(
        name = "DEBUG TESTING NO-CONSENT COMPANY",
        consent = "0"
      )
      assert(result.consent == returnValue.consent)
      assert(result.name == returnValue.name)
    }

    "should return empty company name for EORI starting with GB66" in {
      val service     = new CompanyInformationService
      val result      = service.companyInformation("GB661234567890")
      val returnValue = companyInfo.copy(
        name = ""
      )
      assert(result.name == returnValue.name)
    }

    "should return GB company for EORI starting with GB" in {
      val service     = new CompanyInformationService
      val result      = service.companyInformation("GB123456789012")
      val returnValue = companyInfo.copy(
        name = "DEBUG TESTING GB COMPANY (UK) LTD",
        consent = "1"
      )
      assert(result.consent == returnValue.consent)
      assert(result.name == returnValue.name)
    }

    "should return XI company for EORI starting with XI" in {
      val service     = new CompanyInformationService
      val result      = service.companyInformation("XI123456789012")
      val returnValue = companyInfo.copy(
        name = "DEBUG TESTING XI COMPANY (NIR) LTD",
        consent = "1"
      )
      assert(result.consent == returnValue.consent)
      assert(result.name == returnValue.name)
    }

    "should return default for anything else " in {
      val service = new CompanyInformationService
      val result  = service.companyInformation("foo")

      assert(result.consent == "1")
      assert(result.name == "DEFAULT COMPANY (NOT-REAL-EORI)")
      assert(result.address == companyInfo.address)
    }
  }
}
