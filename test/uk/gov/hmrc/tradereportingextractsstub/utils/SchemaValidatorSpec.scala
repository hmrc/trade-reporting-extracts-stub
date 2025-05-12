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

package uk.gov.hmrc.tradereportingextractsstub.utils

import org.everit.json.schema.loader.SchemaLoader
import org.json.JSONObject
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.{JsObject, Json}
import uk.gov.hmrc.tradereportingextractsstub.utils.SchemaValidator

class SchemaValidatorSpec extends AnyWordSpec with Matchers {

  val TestSchemaValidator = new SchemaValidator

  "SchemaValidator" must {

    "validate a JSON object against a valid schema" in {
      val schema: JSONObject = new JSONObject(
        """
          |{
          |  "type": "object",
          |  "properties": {
          |    "name": { "type": "string" },
          |    "age": { "type": "integer" }
          |  },
          |  "required": ["name", "age"]
          |}
        """.stripMargin
      )

      val validJson: JsObject = Json
        .parse(
          """
          |{
          |  "name": "John Doe",
          |  "age": 30
          |}
        """.stripMargin
        )
        .as[JsObject]

      val result = TestSchemaValidator.validateJson(SchemaLoader.load(schema), validJson)
      result match {
        case Right(isValid) => isValid mustBe true
        case Left(error)    => fail(s"Validation failed with error: $error")
      }
    }

    "fail validation for a JSON object that does not match the schema" in {
      val schema: JSONObject = new JSONObject(
        """
          |{
          |  "type": "object",
          |  "properties": {
          |    "name": { "type": "string" },
          |    "age": { "type": "integer" }
          |  },
          |  "required": ["name", "age"]
          |}
              """.stripMargin
      )

      val invalidJson: JsObject = Json
        .parse(
          """
          |{
          |  "name": "John Doe"
          |}
              """.stripMargin
        )
        .as[JsObject]

      val result = TestSchemaValidator.validateJson(SchemaLoader.load(schema), invalidJson)
      result match {
        case Right(_)    => fail("Validation should have failed but passed")
        case Left(error) => error mustBe "#"
      }
    }
  }
}
