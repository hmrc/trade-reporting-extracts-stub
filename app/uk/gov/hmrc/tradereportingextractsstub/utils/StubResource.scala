/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.tradereportingextractsstub.utils

import play.api.libs.json.Json
import play.api.mvc.*
import play.api.mvc.Results.*
import uk.gov.hmrc.tradereportingextractsstub.models.sdes.FileAvailable

import scala.io.Source
import scala.util.Try

trait StubResource {

  def jsonResourceAsResponse(path: String, eori: String): Result =
    val jsonStr                            = Try(Source.fromResource(path).mkString).getOrElse {
      NotFound(s"Resource not found: $path")
    }
    val jsonStrWithEori                    = jsonStr.toString.replaceAll("\\{\\{EORI_VALUE}}", eori)
    val filesAvailable: Seq[FileAvailable] = Json.parse(jsonStrWithEori).as[Seq[FileAvailable]]
    Ok(Json.toJson(filesAvailable))
}
