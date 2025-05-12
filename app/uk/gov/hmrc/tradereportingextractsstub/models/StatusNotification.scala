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

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

case class StatusNotification(
  component: String,
  notificationType: String,
  code: String,
  description: String,
  statusTimestamp: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
) {
  override def toString: String =
    s"StatusNotification(component=$component, notificationType=$notificationType, code=$code, description=$description, , statusTimestamp=$statusTimestamp)"
}

object StatusNotification {
  // CDAP Notifications
  val FileExt = StatusNotification(
    component = "CDAP",
    notificationType = "INFORMATION",
    code = "FILEEXT",
    description = "CDAP:FileExtraction complete: File ready for transfer"
  )

  val FileExtFail = StatusNotification(
    component = "CDAP",
    notificationType = "ERROR",
    code = "FILEEXTFAIL",
    description =
      "CDAP: There is an error with report file processing. Issue is under investigation by the CDAP live services."
  )

  val FileNoRec = StatusNotification(
    component = "CDAP",
    notificationType = "ERROR",
    code = "FILENOREC",
    description = "CDAP: Report file extracted with zero record, check the report request parameters."
  )

  val ComFail = StatusNotification(
    component = "CDAP",
    notificationType = "ERROR",
    code = "COMFAIL",
    description = "CDAP: Communication failure: Unable to connect to EIS endpoint-Journey TRE04"
  )

  val FileAvailable = StatusNotification(
    component = "CDAP",
    notificationType = "ERROR",
    code = "FILEAVAILABLE",
    description = "CDAP: File extraction complete and ready for EIS file transfer notification."
  )

  // EIS Notifications
  val FileAcc = StatusNotification(
    component = "EIS",
    notificationType = "INFORMATION",
    code = "FILEACC",
    description = "EIS:Business message accepted: Payload received from CDAP."
  )

  val FileNotFnd = StatusNotification(
    component = "EIS",
    notificationType = "ERROR",
    code = "FILENOTFND",
    description = "EIS:File not found, metadata notification incomplete"
  )

  val ChkSumFail = StatusNotification(
    component = "EIS",
    notificationType = "ERROR",
    code = "CHKSUMFAIL",
    description =
      "EIS:System Checksum of the Business Message Payload does not match the checksum supplied by the payload originator"
  )

  val DecryptFail = StatusNotification(
    component = "EIS",
    notificationType = "ERROR",
    code = "DECRYPTFAIL",
    description = "EIS:System attempt to Decrypt the Business Message Payload hasfailed"
  )

  val VirScanFail = StatusNotification(
    component = "EIS",
    notificationType = "ERROR",
    code = "VIRSCNFAIL",
    description = "EIS:System Virus Scan of the Business Message Payload has failed"
  )

  val FileTrnsfrFail = StatusNotification(
    component = "EIS",
    notificationType = "ERROR",
    code = "FILETRNSFRFAIL",
    description =
      "EIS: There is an error with report file processing. Issue is under investigation by the EIS live services."
  )

  val EpDelv = StatusNotification(
    component = "EIS",
    notificationType = "INFORMATION",
    code = "EPDELV",
    description = "EIS:Business Message Payload has successfully been delivered to the Destination Endpoint"
  )

  val EpAcc = StatusNotification(
    component = "EIS",
    notificationType = "INFORMATION",
    code = "EPACC",
    description = "EIS: Business Message Payload has been accepted by the DestinationEndpoint"
  )

  // SDES Notifications
  val EpcFail = StatusNotification(
    component = "SDES",
    notificationType = "ERROR",
    code = "EPCFAIL",
    description = "SDES:Transfer failure between EIS and SDES, File not sent successfully"
  )

  // List of all predefined notifications
  val allNotifications: List[StatusNotification] = List(
    FileExt,
    FileExtFail,
    FileNoRec,
    ComFail,
    FileAvailable,
    FileAcc,
    FileNotFnd,
    ChkSumFail,
    DecryptFail,
    VirScanFail,
    FileTrnsfrFail,
    EpDelv,
    EpAcc,
    EpcFail
  )
}
