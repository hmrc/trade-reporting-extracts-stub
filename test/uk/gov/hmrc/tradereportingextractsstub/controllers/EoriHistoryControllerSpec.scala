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
import play.api.http.Status
import play.api.test.Helpers.*
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.tradereportingextractsstub.controllers.utils.SpecBase
import uk.gov.hmrc.tradereportingextractsstub.services.EoriHistoryService

class EoriHistoryControllerSpec extends SpecBase {
  private val fakeRequest        = FakeRequest("GET", "/eori-history")
  private val eoriHistoryService = new EoriHistoryService
  private val controller         = new EoriHistoryController(eoriHistoryService, Helpers.stubControllerComponents())

  "GET /eori-history" should {
    "return 200" in {
      val result = controller.eoriHistory()(fakeRequest)
      status(result) shouldBe Status.OK
    }
  }
}
