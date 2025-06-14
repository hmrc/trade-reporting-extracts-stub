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
import uk.gov.hmrc.tradereportingextractsstub.models.EoriRequest
import uk.gov.hmrc.tradereportingextractsstub.utils.SpecBase

class EoriHistoryControllerSpec extends SpecBase {

  "GET /eori-history" should {
    "return 200" in new Setup {

      val eoriRequest = EoriRequest(eori = "GB123456789012")
      val request     = FakeRequest(POST, routes.EoriHistoryController.eoriHistory().url)
        .withBody(Json.toJson(eoriRequest))
      val result      = route(app, request).value
      status(result)        shouldBe OK
      contentAsJson(result) shouldBe Json.obj(
        "eoriHistory" -> Json.arr(
          Json.obj(
            "eori"       -> "GB123456789012",
            "validFrom"  -> "2001-01-20",
            "validUntil" -> "2002-01-20"
          )
        )
      )
    }
  }

  trait Setup {
    val app: Application = application.build()
  }
}
