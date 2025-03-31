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

import play.api.libs.json.Json
import play.api.mvc.Result
import play.api.mvc.Results.Ok
import uk.gov.hmrc.tradereportingextractsstub.models.{EoriHistoryResponse, EoriPeriod}

class EoriHistoryService:
  def eoriHistory(): Result = {
    val eori1: String       = "EORI00000001"
    val eori2: String       = "EORI00000002"
    val period1: EoriPeriod = EoriPeriod(eori1, Some("2001-01-20T00:00:00Z"), None)
    val period2: EoriPeriod = EoriPeriod(eori2, Some("2002-01-20T00:00:00Z"), None)

    Ok(Json.toJson(EoriHistoryResponse(Seq(period1, period2))))
  }
