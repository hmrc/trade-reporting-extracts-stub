package uk.gov.hmrc.tradereportingextractsstub.models

class MissingRequiredParameterException(paramName: String, paramType: String)
    extends Exception(s"Missing required $paramType parameter `$paramName`.")
