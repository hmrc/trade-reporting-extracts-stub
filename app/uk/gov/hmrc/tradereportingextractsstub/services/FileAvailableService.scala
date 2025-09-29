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

package uk.gov.hmrc.tradereportingextractsstub.services

import play.api.Logging
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.tradereportingextractsstub.config.AppConfig
import uk.gov.hmrc.tradereportingextractsstub.connectors.TradeReportingExtractsConnector
import uk.gov.hmrc.tradereportingextractsstub.models.availableReport.AvailableReportsViewModel
import uk.gov.hmrc.tradereportingextractsstub.models.sdes.FileAvailableStubRequest

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class FileAvailableService @Inject() (httpClient: HttpClientV2)(implicit
  appConfig: AppConfig,
  ec: ExecutionContext,
  connector: TradeReportingExtractsConnector
) extends Logging {
  def getAvailableReports(eori: String)(implicit
    hc: HeaderCarrier
  ): Future[Seq[FileAvailableStubRequest]] =
    connector.getAvailableReports(eori)
}
