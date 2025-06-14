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
import uk.gov.hmrc.tradereportingextractsstub.models.{EoriHistory, EoriHistoryResponse}

import java.nio.file.{Files, Paths}
import scala.util.{Failure, Success, Try}

class EoriHistoryService {

  private val reportsPath: String = "conf/response/EoriHistoricalData.json"

  def eoriHistory(eori: String): EoriHistoryResponse =
    loadReportsFromFile(reportsPath) match {
      case Success(reports) => EoriHistoryResponse(reports)
      case Failure(ex)      => EoriHistoryResponse(Seq.empty[EoriHistory])
    }

  private def loadReportsFromFile(path: String): Try[Seq[EoriHistory]] =
    Try {
      val jsonString = Files.readString(Paths.get(path))
      Json.parse(jsonString).as[Seq[EoriHistory]]
    }
}
