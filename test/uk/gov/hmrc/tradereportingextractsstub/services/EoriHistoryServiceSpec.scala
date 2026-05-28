package uk.gov.hmrc.tradereportingextractsstub.services

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.tradereportingextractsstub.models.EoriHistoryResponse

class EoriHistoryServiceSpec extends AnyFreeSpec with Matchers {

  "EoriHistoryService" - {

    val service = new EoriHistoryService

    "should return only GB eori hisotry when false" in {
      val result = service.eoriHistory("GB123456789012", false)
      result                       shouldBe a[EoriHistoryResponse]
      result.eoriHistory.map(_.eori) should contain("GBEORI")
      result.eoriHistory.map(_.eori) should not contain "XIFROMSTRATEGIC  "
    }

    "should return GB and XI  eori hisotry when true" in {
      val result = service.eoriHistory("GB123456789012", true)
      result                       shouldBe a[EoriHistoryResponse]
      result.eoriHistory.map(_.eori) should contain("GBEORI")
      result.eoriHistory.map(_.eori) should contain("XIFROMSTRATEGIC")
    }
  }
}
