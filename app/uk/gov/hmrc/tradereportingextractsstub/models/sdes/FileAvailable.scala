package uk.gov.hmrc.tradereportingextractsstub.models.sdes

import play.api.libs.json.*

case class FileAvailable(
  filename: String,
  downloadURL: String,
  fileSize: Long,
  metadata: Seq[FileAvailableMetadataItem]
)

object FileAvailable {
  implicit val format: Format[FileAvailable] =
    Json.format[FileAvailable]
}
