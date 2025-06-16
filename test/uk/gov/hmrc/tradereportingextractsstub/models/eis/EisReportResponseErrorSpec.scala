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

package uk.gov.hmrc.tradereportingextractsstub.models.eis

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.*

class EisReportResponseErrorSpec extends AnyWordSpec with Matchers {
  "EisReportResponseError and related formats" should {
    "serialize and deserialize correctly" in {
      val sourceFault = EisReportResponseErrorDetailSourceFaultDetail(
        detail = List("Failed header validation: Invalid x-forwarded-host header"),
        restFault = Some(Json.obj("code" -> "REST_FAULT")),
        soapFault = None
      )
      val detail      = EisReportResponseErrorDetail(
        correlationId = "abc-123",
        errorCode = Some("400"),
        errorMessage = Some("Failed header validation"),
        source = Some("EIS"),
        sourceFaultDetail = Some(sourceFault),
        timestamp = "2025-06-14T20:02:32Z"
      )
      val error       = EisReportResponseError(detail)
      val json        = Json.toJson(error)
      json.as[EisReportResponseError] shouldBe error
    }
  }
}
