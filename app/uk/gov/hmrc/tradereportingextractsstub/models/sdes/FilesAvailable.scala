package uk.gov.hmrc.tradereportingextractsstub.models.sdes

import play.api.libs.json.*

case class FilesAvailable(files: Seq[FileAvailable])

object FilesAvailable {
  implicit val format: Format[FilesAvailable] = Json.format[FilesAvailable]
}
