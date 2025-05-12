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

package uk.gov.hmrc.tradereportingextractsstub.connectors

import play.api.libs.json.Json
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse}
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.tradereportingextractsstub.config.AppConfig

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.ws.JsonBodyWritables.writeableOf_JsValue
import uk.gov.hmrc.http.StringContextOps

class TREConnector @Inject() (appConfig: AppConfig, httpClient: HttpClientV2)(implicit ec: ExecutionContext) {

  def sendStatusUpdate(headers: Seq[(String, String)], body: Map[String, String])(implicit
    hc: HeaderCarrier
  ): Future[Boolean] = {
    val url = url"${appConfig.tradeReportingExtractsApi}/get-status-update"
    httpClient
      .put(url)
      .setHeader(headers: _*)
      .withBody(Json.toJson(body))
      .execute[HttpResponse]
      .map(_.status == 200)
      .recover { case _ => false }
  }
}
