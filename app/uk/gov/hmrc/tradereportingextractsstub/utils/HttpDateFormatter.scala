package uk.gov.hmrc.tradereportingextractsstub.utils

import java.time.format.DateTimeFormatter
import java.time.{ZoneId, ZonedDateTime}

object HttpDateFormatter {
  private val httpDateFormat: DateTimeFormatter =
    DateTimeFormatter
      .ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'")
      .withZone(ZoneId.of("GMT"))

  def getCurrentHttpDate: String =
    httpDateFormat.format(ZonedDateTime.now())
}
