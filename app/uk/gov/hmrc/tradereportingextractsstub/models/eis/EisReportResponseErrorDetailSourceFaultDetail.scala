package uk.gov.hmrc.tradereportingextractsstub.models.eis

import play.api.libs.json.*

case class EisReportResponseErrorDetailSourceFaultDetail(
  detail: List[String],
  restFault: Option[JsObject],
  soapFault: Option[JsObject]
)

object EisReportResponseErrorDetailSourceFaultDetail {
  implicit lazy val eisReportResponseErrorDetailSourceFaultDetailJsonFormat
    : Format[EisReportResponseErrorDetailSourceFaultDetail] = Json.format[EisReportResponseErrorDetailSourceFaultDetail]
}
