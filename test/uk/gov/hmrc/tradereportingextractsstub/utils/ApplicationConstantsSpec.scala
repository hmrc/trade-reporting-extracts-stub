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

package uk.gov.hmrc.tradereportingextractsstub.utils

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ApplicationConstantsSpec extends AnyWordSpec with Matchers {

  "ApplicationConstants" must {

    "have the correct value for CorrelationIdKey" in {
      ApplicationConstants.CorrelationIdKey mustBe "X-Correlation-ID"
    }

    "have the correct value for SourceSystem" in {
      ApplicationConstants.SourceSystem mustBe "Source-System"
    }

    "have the correct value for XTransmittingSystem" in {
      ApplicationConstants.XTransmittingSystem mustBe "X-Transmitting-System"
    }

    "have the correct value for Date" in {
      ApplicationConstants.Date mustBe "Date"
    }

    "have the correct value for ContentType" in {
      ApplicationConstants.ContentType mustBe "Content-Type"
    }

    "have the correct value for applicationComponent" in {
      ApplicationConstants.applicationComponent mustBe "applicationComponent"
    }

    "have the correct value for statusType" in {
      ApplicationConstants.statusType mustBe "statusType"
    }

    "have the correct value for statusCode" in {
      ApplicationConstants.statusCode mustBe "statusCode"
    }

    "have the correct value for statusMessage" in {
      ApplicationConstants.statusMessage mustBe "statusMessage"
    }

    "have the correct value for IF" in {
      ApplicationConstants.IF mustBe "IF"
    }

    "have the correct value for contentJson" in {
      ApplicationConstants.contentJson mustBe "application/json"
    }
  }
}
