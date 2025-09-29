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

package uk.gov.hmrc.tradereportingextractsstub.models.availableReport

import play.api.libs.functional.syntax.*
import play.api.libs.json.{Format, Json, OFormat, __}
import uk.gov.hmrc.tradereportingextractsstub.models.{FileType, ReportTypeName}
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale

case class AvailableReportsViewModel(
  availableUserReports: Option[Seq[AvailableUserReportsViewModel]],
  availableThirdPartyReports: Option[Seq[AvailableThirdPartyReportsViewModel]]
)

object AvailableReportsViewModel {
  implicit lazy val format: Format[AvailableReportsViewModel] = (
    (__ \ "availableUserReports").formatNullable[Seq[AvailableUserReportsViewModel]] and
      (__ \ "availableThirdPartyReports").formatNullable[Seq[AvailableThirdPartyReportsViewModel]]
  )(AvailableReportsViewModel.apply, o => Tuple.fromProductTyped(o))
}

case class AvailableThirdPartyReportsViewModel(
  reportName: String,
  referenceNumber: String,
  expiryDate: Instant,
  reportType: ReportTypeName,
  companyName: String,
  action: Seq[AvailableReportAction]
)

object AvailableThirdPartyReportsViewModel {
  implicit val format: OFormat[AvailableThirdPartyReportsViewModel] = Json.format[AvailableThirdPartyReportsViewModel]
}

case class AvailableUserReportsViewModel(
  reportName: String,
  referenceNumber: String,
  expiryDate: Instant,
  reportType: ReportTypeName
) {
  def formattedExpiryDate: String =
    AvailableUserReportsViewModel.dateFormatter.format(expiryDate.atZone(java.time.ZoneOffset.UTC).toLocalDate)
  def formattedReportType: String = AvailableUserReportsViewModel.getReportType(reportType)

}

object AvailableUserReportsViewModel {
  private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM uuuu", Locale.ENGLISH)

  def formatExpiryDate(expiryDate: Instant): String =
    dateFormatter.format(expiryDate.atZone(java.time.ZoneOffset.UTC).toLocalDate)

  def getReportType(reportType: ReportTypeName): String =
    Option(reportType)
      .map {
        case ReportTypeName.IMPORTS_HEADER_REPORT  => "Import header"
        case ReportTypeName.IMPORTS_ITEM_REPORT    => "Import item"
        case ReportTypeName.IMPORTS_TAXLINE_REPORT => "Import tax line"
        case ReportTypeName.EXPORTS_ITEM_REPORT    => "Export item"
      }
      .getOrElse(throw new IllegalArgumentException("Unknown or null report type"))

  implicit val format: OFormat[AvailableUserReportsViewModel] =
    Json.format[AvailableUserReportsViewModel]

}

case class AvailableReportAction(
  fileName: String,
  fileURL: String,
  size: Long,
  fileType: FileType
)

object AvailableReportAction {
  implicit val format: OFormat[AvailableReportAction] = Json.format[AvailableReportAction]
}
