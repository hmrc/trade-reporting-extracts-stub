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
import org.scalatest.wordspec.AnyWordSpec
import play.api.http.Status
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.tradereportingextractsstub.services.{CompanyInformationService, NotificationEmailService}

class NotificationEmailControllerSpec extends AnyWordSpec, Matchers:

  private val fakeRequest = FakeRequest("GET", "/verified-email")
  private val notificationEmailService = new NotificationEmailService
  private val controller = new NotificationEmailController(notificationEmailService, Helpers.stubControllerComponents())

  "GET /verified-email" should:
    "return 200" in:
      val result = controller.notificationEmail("AB000000029")(fakeRequest)
      status(result) shouldBe Status.OK
