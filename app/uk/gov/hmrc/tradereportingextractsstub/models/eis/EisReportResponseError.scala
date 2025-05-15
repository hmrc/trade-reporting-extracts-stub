package uk.gov.hmrc.tradereportingextractsstub.models.eis

import play.api.libs.json.*

case class EisReportResponseError(
  errorDetail: EisReportResponseErrorDetail
)

object EisReportResponseError {
  implicit lazy val eisReportResponseJsonFormat: Format[EisReportResponseError] =
    Json.format[EisReportResponseError]
}
