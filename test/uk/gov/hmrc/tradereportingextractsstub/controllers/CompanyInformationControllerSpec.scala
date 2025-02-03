package uk.gov.hmrc.tradereportingextractsstub.controllers

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.http.Status
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.tradereportingextractsstub.services.CompanyInformationService

class CompanyInformationControllerSpec extends AnyWordSpec with Matchers {

  private val fakeRequest = FakeRequest("GET", "/company-information")
  private val companyInformationService = new CompanyInformationService
  private val controller = new CompanyInformationController(companyInformationService, Helpers.stubControllerComponents())

  "GET /company-information" should {
    "return 200" in {
      val result = controller.companyInformation()(fakeRequest)
      status(result) shouldBe Status.OK
    }
  }
}
