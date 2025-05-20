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

import play.api.Configuration
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

import javax.inject.{Inject, Singleton}

@Singleton
class AppConfig @Inject() (configuration: Configuration, servicesConfig: ServicesConfig):
  val appName: String                        = configuration.get[String]("appName")
  val host: String                           = configuration.get[String]("host")
  val statusNotificationDelay: Int           = configuration.get[Int]("notificationDelay")
  lazy val tradeReportingExtractsApi: String = servicesConfig.baseUrl("trade-reporting-extracts") +
    configuration.get[String]("microservice.services.trade-reporting-extracts.context")
  lazy val eisAuthToken: String              = configuration.get[String]("eis.auth.token")

  lazy val treXClientId: String       = configuration.get[String]("sdes.client-id")
  lazy val treInformationType: String = configuration.get[String]("sdes.information-type")
