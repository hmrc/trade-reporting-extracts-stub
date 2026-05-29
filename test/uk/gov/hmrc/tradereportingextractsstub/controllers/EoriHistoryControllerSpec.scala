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

import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.shouldBe
import play.api.Application
import play.api.libs.json.Json
import play.api.test.Helpers.*
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.tradereportingextractsstub.models.{EoriHistory, EoriHistoryResponse, EoriRequest}
import uk.gov.hmrc.tradereportingextractsstub.utils.SpecBase

import java.time.LocalDate

class EoriHistoryControllerSpec extends SpecBase {

  "GET /eori/eori-history" should {
    "return 200 from eoriTraderHistory" in new Setup {

      val eoriRequest = EoriRequest(eori = "GB123456789012")
      val request     = FakeRequest(GET, routes.EoriHistoryController.eoriTraderHistory().url)
        .withBody(Json.toJson(eoriRequest))
      val result      = route(app, request).value
      status(result) shouldBe OK
      val expectedEoriHistory = EoriHistoryResponse(
        eoriHistory = Seq(
          EoriHistory(
            eori = "GBEORI",
            validFrom = Some(LocalDate.parse("2025-01-01")),
            validUntil = Some(LocalDate.parse("2025-12-31"))
          )
        )
      )
      val actualEoriHistory   = contentAsJson(result).as[EoriHistoryResponse]
      actualEoriHistory shouldBe expectedEoriHistory
    }
  }

  "GET /eori/gbxi-eori-history" should {
    "return 200 from eoriTraderHistoryStrategic" in new Setup {
      val eoriRequest = EoriRequest(eori = "GB123456789012")
      val request     = FakeRequest(GET, routes.EoriHistoryController.eoriTraderHistoryStrategic().url)
        .withBody(Json.toJson(eoriRequest))
      val result      = route(app, request).value
      status(result) shouldBe OK
      val expectedEoriHistory = EoriHistoryResponse(
        eoriHistory = Seq(
          EoriHistory(
            eori = "GBEORI",
            validFrom = Some(LocalDate.parse("2025-01-01")),
            validUntil = Some(LocalDate.parse("2025-12-31"))
          ),
          EoriHistory(
            eori = "XIFROMSTRATEGIC",
            validFrom = Some(LocalDate.parse("2025-01-01")),
            validUntil = Some(LocalDate.parse("2025-12-31"))
          )
        )
      )
      val actualEoriHistory   = contentAsJson(result).as[EoriHistoryResponse]
      actualEoriHistory shouldBe expectedEoriHistory
    }
  }

  "POST /eori/eori-history-third-party" should {
    "return 200 from eoriHistory" in new Setup {

      val eoriRequest = EoriRequest(eori = "GB123456789012")
      val request     = FakeRequest(POST, routes.EoriHistoryController.eoriHistory().url)
        .withBody(Json.toJson(eoriRequest))
      val result      = route(app, request).value
      status(result) shouldBe OK
      val expectedEoriHistory = EoriHistoryResponse(
        eoriHistory = Seq(
          EoriHistory(
            eori = "GBEORI",
            validFrom = Some(LocalDate.parse("2025-01-01")),
            validUntil = Some(LocalDate.parse("2025-12-31"))
          )
        )
      )
      val actualEoriHistory   = contentAsJson(result).as[EoriHistoryResponse]
      actualEoriHistory shouldBe expectedEoriHistory
    }
  }

  "POST /eori/gbxi-eori-history-third-party" should {
    "return 200 and GB only when eori begis with anything but GB9 from eoriHistoryStrategic" in new Setup {

      val eoriRequest = EoriRequest(eori = "GB123456789012")
      val request     = FakeRequest(POST, routes.EoriHistoryController.eoriHistoryStrategic().url)
        .withBody(Json.toJson(eoriRequest))
      val result      = route(app, request).value
      status(result) shouldBe OK
      val expectedEoriHistory = EoriHistoryResponse(
        eoriHistory = Seq(
          EoriHistory(
            eori = "GBEORI",
            validFrom = Some(LocalDate.parse("2025-01-01")),
            validUntil = Some(LocalDate.parse("2025-12-31"))
          )
        )
      )
      val actualEoriHistory   = contentAsJson(result).as[EoriHistoryResponse]
      actualEoriHistory shouldBe expectedEoriHistory
    }

    "return 200 and GB/XI when eori begis with GB9 from eoriHistoryStrategic" in new Setup {

      val eoriRequest = EoriRequest(eori = "GB9")
      val request     = FakeRequest(POST, routes.EoriHistoryController.eoriHistoryStrategic().url)
        .withBody(Json.toJson(eoriRequest))
      val result      = route(app, request).value
      status(result) shouldBe OK
      val expectedEoriHistory = EoriHistoryResponse(
        eoriHistory = Seq(
          EoriHistory(
            eori = "GBEORI",
            validFrom = Some(LocalDate.parse("2025-01-01")),
            validUntil = Some(LocalDate.parse("2025-12-31"))
          ),
          EoriHistory(
            eori = "XIFROMSTRATEGIC",
            validFrom = Some(LocalDate.parse("2025-01-01")),
            validUntil = Some(LocalDate.parse("2025-12-31"))
          )
        )
      )
      val actualEoriHistory   = contentAsJson(result).as[EoriHistoryResponse]
      actualEoriHistory shouldBe expectedEoriHistory
    }
  }

  trait Setup {
    val app: Application = application.build()
  }
}
