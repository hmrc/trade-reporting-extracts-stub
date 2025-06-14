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

package uk.gov.hmrc.tradereportingextractsstub.config

import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers.shouldBe
import org.scalatestplus.mockito.MockitoSugar
import play.api.Application
import uk.gov.hmrc.tradereportingextractsstub.utils.SpecBase

class AppConfigSpec extends SpecBase with MockitoSugar {

  "AppConfig" should {

    "return the correct appName" in new Setup {
      appConfig.appName shouldBe "trade-reporting-extracts-stub"
    }
    "return the correct eisAuthToken" in new Setup {
      appConfig.eisAuthToken shouldBe "EisAuthToken"
    }
    "return the correct treXClientId" in new Setup {
      appConfig.treXClientId shouldBe "TRE-CLIENT-ID"
    }
    "return the correct treInformationType" in new Setup {
      appConfig.treInformationType shouldBe "TRE"
    }

  }

  trait Setup {
    val app: Application     = application.build()
    val appConfig: AppConfig = app.injector.instanceOf[AppConfig]
  }
}
