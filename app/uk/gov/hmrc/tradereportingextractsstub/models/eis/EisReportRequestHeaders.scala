package uk.gov.hmrc.tradereportingextractsstub.models.eis

object EisReportRequestHeaders extends Enumeration {
  type ReportRequestHeaders = Value

  val Accept: Value         = Value("accept")
  val Authorization: Value  = Value("authorization")
  val ContentType: Value    = Value("content-type")
  val Date: Value           = Value("date")
  val XCorrelationID: Value = Value("x-correlation-id")
  val XForwardedHost: Value = Value("x-forwarded-host")

  def allHeaders: List[String] = values.map(_.toString).toList
}
