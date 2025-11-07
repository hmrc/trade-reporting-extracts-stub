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

package uk.gov.hmrc.tradereportingextractsstub.services

import uk.gov.hmrc.tradereportingextractsstub.models.{AddressInformation, CompanyInformation}

class CompanyInformationService:

  def companyInformation(eori: String): CompanyInformation =
    
    var companyInfo: String = "DEFAULT COMPANY (NOT-REAL-EORI)"
    var consent: String = "1"

    eori match {
      case eori if eori.startsWith("GB999999999999") => { // "No consent"
        companyInfo = "DEBUG TESTING NO-CONSENT COMPANY" 
        consent = "0"
      }
      case eori if eori.startsWith("GB666666666666") => { // "Enter a real EORI number error"
        companyInfo = "" 
      }
      case eori if eori.startsWith("GB") => {
        companyInfo = "DEBUG TESTING GB COMPANY (UK) LTD" 
      } 
      case eori if eori.startsWith("XI") => {
        companyInfo = "DEBUG TESTING XI COMPANY (NIR) LTD"
      } 
      case _  => {} 
    }

    return CompanyInformation(
      companyInfo,
      consent,
      AddressInformation(
        "XYZ Street",
        "ABC City",
        Some("G11 2ZZ"),
        "GB"
      )
    )
