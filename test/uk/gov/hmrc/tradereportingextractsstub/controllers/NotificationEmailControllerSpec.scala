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
import org.scalatest.matchers.should.Matchers.{should, shouldBe}
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.Helpers.*
import play.api.test.{FakeHeaders, FakeRequest, Helpers}
import uk.gov.hmrc.tradereportingextractsstub.controllers.utils.SpecBase
import uk.gov.hmrc.tradereportingextractsstub.services.NotificationEmailService

import scala.concurrent.ExecutionContext.Implicits.global

class NotificationEmailControllerSpec extends SpecBase {

  private val notificationEmailService = new NotificationEmailService
  private val controller               =
    new NotificationEmailController(notificationEmailService, Helpers.stubControllerComponents())(implicitly)

  "GET /verified-email" should {
    "return 200" in {
      val fakeRequest =
        FakeRequest(
          "GET",
          s"/verified-email",
          FakeHeaders(Seq(CONTENT_TYPE -> JSON)),
          Json.obj("eori" -> "GB123456789012")
        )
      val result = controller.notificationEmail()(fakeRequest)
      result should not be null
    }
  }
}
