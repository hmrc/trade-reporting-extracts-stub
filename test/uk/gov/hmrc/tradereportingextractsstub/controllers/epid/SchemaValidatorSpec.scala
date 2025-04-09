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

package uk.gov.hmrc.tradereportingextractsstub.controllers.epid

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import play.api.libs.json.{JsValue, Json}

class SchemaValidatorSpec extends AnyWordSpec with Matchers with MockitoSugar {

  object TestSchemaValidator extends SchemaValidator

  "SchemaValidator" should {
    "return Right(true) for valid JSON" in {
      val validJson: JsValue = Json.parse(
        """
          |{
          |  "requestID": "CC12345678",
          |  "requestTimestamp": "2024-11-27T17:48:19.123Z",
          |  "reportTypeName": "IMPORTS-ITEM-REPORT",
          |  "requesterEori": "GB111111111000",
          |  "eori": ["GB123456789", "FR987654321"],
          |  "eoriRole": "DECLARANT",
          |  "startDate": "2024-10-10",
          |  "endDate": "2024-11-09"
          |}
        """.stripMargin
      )

      TestSchemaValidator.isJsonValid(validJson) shouldBe Right(true)
    }

    "return Left with schema location for invalid JSON" in {
      val invalidJson: JsValue = Json.parse(
        """
          |{
          |  "requestID": "CC12345678",
          |  "requestTimestamp": "2024-11-27T17:48:19.123Z"
          |}
        """.stripMargin
      )

      val result = TestSchemaValidator.isJsonValid(invalidJson)
      result.swap.getOrElse("") should not be empty
    }
  }
}
