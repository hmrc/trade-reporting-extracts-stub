package uk.gov.hmrc.tradereportingextractsstub.controllers

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.http.Status
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.tradereportingextractsstub.services.{CompanyInformationService, NotificationEmailService}

class NotificationEmailControllerSpec extends AnyWordSpec with Matchers {

  private val fakeRequest = FakeRequest("GET", "/verified-email")
  private val notificationEmailService = new NotificationEmailService
  private val controller = new NotificationEmailController(notificationEmailService, Helpers.stubControllerComponents())

  "GET /verified-email" should {
    "return 200" in {
      val result = controller.notificationEmail()(fakeRequest)
      status(result) shouldBe Status.OK
    }
  }
}
