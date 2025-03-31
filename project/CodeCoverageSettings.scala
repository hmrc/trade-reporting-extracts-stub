import sbt.Setting
import scoverage.ScoverageKeys

object CodeCoverageSettings {

  private val excludedPackages: Seq[String] = Seq(
    "<empty>",
    "Reverse.*",
    "uk.gov.hmrc.BuildInfo",
    "app.*",
    "prod.*",
    ".*Routes.*",
    ".*RoutesPrefix.*",
    "testOnly.*",
    "testOnlyDoNotUseInAppConf.*",
    "uk.gov.hmrc.tradereportingextractsstub.config",
    ".*javascript.*",
  )

  private val excludedFiles: Seq[String] = Seq(
    "<empty>",
    ".*javascript.*",
    ".*Routes.*",
    ".*testonly.*"
  )

  val settings: Seq[Setting[_]] = Seq(
    ScoverageKeys.coverageExcludedPackages := excludedPackages.mkString(";"),
    ScoverageKeys.coverageExcludedFiles := excludedFiles.mkString(";"),
    ScoverageKeys.coverageMinimumStmtTotal := 79,
    ScoverageKeys.coverageFailOnMinimum := false,
    ScoverageKeys.coverageHighlighting := true
  )
}
