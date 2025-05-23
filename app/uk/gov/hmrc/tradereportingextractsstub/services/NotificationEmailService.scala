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
import play.api.mvc.Results.{Forbidden, Ok}
import uk.gov.hmrc.tradereportingextractsstub.models.{AllowedEoris, NotificationEmail}

import java.time.LocalDateTime

class NotificationEmailService extends AllowedEoris:

  def notificationEmail(eori: String): Result =
    if !allowedEoris.contains(eori) then return Forbidden("EORI not allowed")
    val res = NotificationEmail(
      "example@test.com",
      LocalDateTime.now()
    )
    Ok(Json.toJson(res))
