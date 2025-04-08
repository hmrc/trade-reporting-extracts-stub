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

package services

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.Json
import uk.gov.hmrc.tradereportingextractsstub.services.SchemaValidator

class SchemaValidatorSpec extends AnyWordSpec with Matchers {

  "SchemaValidator" should {

    "validate a correct JSON" in {
      val schemaValidator = new SchemaValidator()
      val validJson = Json.parse(
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
        """.stripMargin)

      schemaValidator.isJsonValid(validJson) shouldBe Right(true)
    }

    "invalidate an incorrect JSON" in {
      val schemaValidator = new SchemaValidator()
      val invalidJson = Json.parse(
        """
          |{
          |  "requestID": "CC12345678",
          |  "requestTimestamp": "2024-11-27T17:48:19.123Z",
          |  "reportTypeName": "IMPORTS-ITEM-REPORT",
          |  "requesterEori": "GB111111111000",
          |  "eori": ["GB123456789", "FR987654321"],
          |  "eoriRole": "DECLARANT",
          |  "startDate": "2024-10-1022",
          |  "endDate": "2024-11-0922"
          |}
        """.stripMargin)

      schemaValidator.isJsonValid(invalidJson).isLeft shouldBe true
    }
  }
}