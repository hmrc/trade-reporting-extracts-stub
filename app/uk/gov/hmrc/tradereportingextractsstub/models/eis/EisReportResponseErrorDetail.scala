package uk.gov.hmrc.tradereportingextractsstub.models.eis

import play.api.libs.json.*

case class EisReportResponseErrorDetail(
  correlationId: String,
  errorCode: Option[String],
  errorMessage: Option[String],
  source: Option[String],
  sourceFaultDetail: Option[EisReportResponseErrorDetailSourceFaultDetail],
  timestamp: String
)

object EisReportResponseErrorDetail {
  implicit lazy val eisReportResponseErrorDetailJsonFormat: Format[EisReportResponseErrorDetail] =
    Json.format[EisReportResponseErrorDetail]
}
