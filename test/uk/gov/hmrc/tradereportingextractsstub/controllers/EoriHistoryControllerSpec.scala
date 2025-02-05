package uk.gov.hmrc.tradereportingextractsstub.controllers

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.http.Status
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.tradereportingextractsstub.services.{EoriHistoryService, NotificationEmailService}

class EoriHistoryControllerSpec extends AnyWordSpec with Matchers {

  private val fakeRequest = FakeRequest("GET", "/eori-history")
  private val eoriHistoryService = new EoriHistoryService
  private val controller = new EoriHistoryController(eoriHistoryService, Helpers.stubControllerComponents())

  "GET /verified-email" should {
    "return 200" in {
      val result = controller.eoriHistory()(fakeRequest)
      status(result) shouldBe Status.OK
    }
  }
}
