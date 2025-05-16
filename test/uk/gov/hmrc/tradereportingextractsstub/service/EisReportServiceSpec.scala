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

package uk.gov.hmrc.tradereportingextractsstub.service

import org.everit.json.schema.Schema
import org.mockito.Mockito.*
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AsyncWordSpec
import org.scalatestplus.mockito.MockitoSugar
import play.api.libs.json.Json
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.tradereportingextractsstub.services.{EisReportService, StatusNotificationService}
import uk.gov.hmrc.tradereportingextractsstub.utils.SchemaValidator

import scala.compiletime.ops.any
import scala.concurrent.Future

class EisReportServiceSpec extends AsyncWordSpec with Matchers with MockitoSugar with ScalaFutures {

  "EpidReportService" must {

    "return Right(true) and schedule a status update when JSON is valid" in {
      val mockStatusUpdateService = mock[StatusNotificationService]
      val mockSchemaValidator     = mock[SchemaValidator]
      val service                 = new EisReportService(mockStatusUpdateService, mockSchemaValidator)

      val validJson  = Json.parse("""{ "key": "value" }""")
      val mockSchema = mock[Schema]

      when(mockSchemaValidator.loadSchema("/schemas/API1Request.json")).thenReturn(mockSchema)
      when(mockSchemaValidator.validateJson(mockSchema, validJson)).thenReturn(Right(true))

      implicit val hc: HeaderCarrier = HeaderCarrier()

      service.isJsonValid(validJson, hc).map { result =>
        result mustBe Right(true)
      }
    }

    "return Left(error) when JSON is invalid" in {
      val mockStatusUpdateService = mock[StatusNotificationService]
      val mockSchemaValidator     = mock[SchemaValidator]
      val service                 = new EisReportService(mockStatusUpdateService, mockSchemaValidator)

      val invalidJson = Json.parse("""{ "invalid": "data" }""")
      val mockSchema  = mock[Schema]
      when(mockSchemaValidator.loadSchema("/schemas/API1Request.json")).thenReturn(mockSchema)
      when(mockSchemaValidator.validateJson(mockSchema, invalidJson)).thenReturn(Left("Invalid JSON"))

      implicit val hc: HeaderCarrier = HeaderCarrier()
      service.isJsonValid(invalidJson, hc).map { result =>
        result mustBe Left("Invalid JSON")
      }
    }
  }
}
