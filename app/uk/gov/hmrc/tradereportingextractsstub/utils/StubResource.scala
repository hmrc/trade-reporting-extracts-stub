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

import play.api.http.ContentTypes.JSON
import play.api.mvc.*
import play.api.mvc.Results.*

import scala.io.Source
import scala.util.Try

trait StubResource {

  def findResource(path: String): Option[String] = Try(Source.fromResource(path).getLines().mkString("\n")).toOption

  def jsonResourceAsResponse(path: String, postProc: String => String = inp => inp): Result =
    findResource(path).fold[Result](NotFound)(content => Ok(postProc(content)).as(JSON))

}
