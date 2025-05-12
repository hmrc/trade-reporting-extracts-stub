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

package uk.gov.hmrc.tradereportingextractsstub.models

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StatusNotificationSpec extends AnyWordSpec with Matchers {

  "StatusNotification" must {

    "have correct values for predefined notifications" in {
      StatusNotification.FileExt mustBe StatusNotification(
        component = "CDAP",
        notificationType = "INFORMATION",
        code = "FILEEXT",
        description = "CDAP:FileExtraction complete: File ready for transfer"
      )

      StatusNotification.FileExtFail mustBe StatusNotification(
        component = "CDAP",
        notificationType = "ERROR",
        code = "FILEEXTFAIL",
        description =
          "CDAP: There is an error with report file processing. Issue is under investigation by the CDAP live services."
      )

      StatusNotification.FileNoRec mustBe StatusNotification(
        component = "CDAP",
        notificationType = "ERROR",
        code = "FILENOREC",
        description = "CDAP: Report file extracted with zero record, check the report request parameters."
      )

      StatusNotification.ComFail mustBe StatusNotification(
        component = "CDAP",
        notificationType = "ERROR",
        code = "COMFAIL",
        description = "CDAP: Communication failure: Unable to connect to EIS endpoint-Journey TRE04"
      )

      StatusNotification.FileAvailable mustBe StatusNotification(
        component = "CDAP",
        notificationType = "ERROR",
        code = "FILEAVAILABLE",
        description = "CDAP: File extraction complete and ready for EIS file transfer notification."
      )

      StatusNotification.FileAcc mustBe StatusNotification(
        component = "EIS",
        notificationType = "INFORMATION",
        code = "FILEACC",
        description = "EIS:Business message accepted: Payload received from CDAP."
      )

      StatusNotification.FileNotFnd mustBe StatusNotification(
        component = "EIS",
        notificationType = "ERROR",
        code = "FILENOTFND",
        description = "EIS:File not found, metadata notification incomplete"
      )

      StatusNotification.ChkSumFail mustBe StatusNotification(
        component = "EIS",
        notificationType = "ERROR",
        code = "CHKSUMFAIL",
        description =
          "EIS:System Checksum of the Business Message Payload does not match the checksum supplied by the payload originator"
      )

      StatusNotification.DecryptFail mustBe StatusNotification(
        component = "EIS",
        notificationType = "ERROR",
        code = "DECRYPTFAIL",
        description = "EIS:System attempt to Decrypt the Business Message Payload hasfailed"
      )

      StatusNotification.VirScanFail mustBe StatusNotification(
        component = "EIS",
        notificationType = "ERROR",
        code = "VIRSCNFAIL",
        description = "EIS:System Virus Scan of the Business Message Payload has failed"
      )

      StatusNotification.FileTrnsfrFail mustBe StatusNotification(
        component = "EIS",
        notificationType = "ERROR",
        code = "FILETRNSFRFAIL",
        description =
          "EIS: There is an error with report file processing. Issue is under investigation by the EIS live services."
      )

      StatusNotification.EpDelv mustBe StatusNotification(
        component = "EIS",
        notificationType = "INFORMATION",
        code = "EPDELV",
        description = "EIS:Business Message Payload has successfully been delivered to the Destination Endpoint"
      )

      StatusNotification.EpAcc mustBe StatusNotification(
        component = "EIS",
        notificationType = "INFORMATION",
        code = "EPACC",
        description = "EIS: Business Message Payload has been accepted by the DestinationEndpoint"
      )

      StatusNotification.EpcFail mustBe StatusNotification(
        component = "SDES",
        notificationType = "ERROR",
        code = "EPCFAIL",
        description = "SDES:Transfer failure between EIS and SDES, File not sent successfully"
      )
    }

    "return the correct string representation for a notification" in {
      val notification = StatusNotification(
        component = "TestComponent",
        notificationType = "INFO",
        code = "TESTCODE",
        description = "This is a test notification."
      )

      notification.toString mustBe "StatusNotification(component=TestComponent, notificationType=INFO, code=TESTCODE, description=This is a test notification., , statusTimestamp=" + notification.statusTimestamp + ")"
    }

    "contain all predefined notifications in the allNotifications list" in {
      StatusNotification.allNotifications must contain allOf (
        StatusNotification.FileExt,
        StatusNotification.FileExtFail,
        StatusNotification.FileNoRec,
        StatusNotification.ComFail,
        StatusNotification.FileAvailable,
        StatusNotification.FileAcc,
        StatusNotification.FileNotFnd,
        StatusNotification.ChkSumFail,
        StatusNotification.DecryptFail,
        StatusNotification.VirScanFail,
        StatusNotification.FileTrnsfrFail,
        StatusNotification.EpDelv,
        StatusNotification.EpAcc,
        StatusNotification.EpcFail
      )
    }
  }
}
