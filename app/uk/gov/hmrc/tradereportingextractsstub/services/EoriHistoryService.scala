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

import play.api.i18n.Lang.logger
import play.api.libs.json.Json
import play.api.mvc.Result
import play.api.mvc.Results.{Forbidden, Ok}
import uk.gov.hmrc.tradereportingextractsstub.models.{AllowedEoris, EoriHistory, EoriHistoryResponse}

import java.nio.file.{Files, Paths}
import javax.inject.Inject
import scala.util.{Failure, Success, Try}

class EoriHistoryService @Inject() () extends AllowedEoris {

  private val reportsPath: String = "conf/response/EoriHistoricalData.json"

  def eoriHistory(eori: String): Result =
    if (!allowedEoris.contains(eori)) {
      Forbidden("EORI not allowed")
    } else {
      loadReportsFromFile(reportsPath) match {
        case Success(reports) => Ok(Json.toJson(EoriHistoryResponse(reports)))
        case Failure(ex)      =>
          val errMsg = s"Failed to load reports from file: ${ex.getMessage}"
          logger.error(errMsg)
          Forbidden(errMsg)
      }
    }

  private def loadReportsFromFile(path: String): Try[Seq[EoriHistory]] =
    Try {
      val jsonString = Files.readString(Paths.get(path))
      Json.parse(jsonString).as[Seq[EoriHistory]]
    }
}
