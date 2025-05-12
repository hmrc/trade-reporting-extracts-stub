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

import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.Configuration
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig
import uk.gov.hmrc.tradereportingextractsstub.config.AppConfig

class AppConfigSpec extends AnyWordSpec with Matchers with MockitoSugar {

  "AppConfig" must {

    "return the correct appName from configuration" in {
      val mockServicesConfig: ServicesConfig = mock[ServicesConfig]
      val mockConfiguration: Configuration   = mock[Configuration]
      when(mockConfiguration.get[String]("appName")).thenReturn("trade-reporting-extracts")
      when(mockConfiguration.get[String]("host")).thenReturn("localhost")
      when(mockServicesConfig.baseUrl("trade-reporting-extracts")).thenReturn("http://localhost:2100")
      when(mockConfiguration.get[String]("microservice.services.trade-reporting-extracts.context"))
        .thenReturn("/trade-reporting-extracts")
      val appConfig                          = new AppConfig(mockConfiguration, mockServicesConfig)
      appConfig.tradeReportingExtractsApi shouldBe "http://localhost:2100/trade-reporting-extracts"
    }
  }
}
