# trade-reporting-extracts-stub

This repository provides stub APIs that simulate the responses of various microservice APIs consumed by Trade Reporting Extracts. It is designed to facilitate development and testing by mimicking the behavior of dependent services without requiring access to live systems.

## Running the Service Locally

You can run the service locally in two ways:

### 1. Using sbt
After cloning the repository, run the following command in the root directory:

```
sbt run
```

### 2. Using Service Manager CLI
To use the service manager CLI (sm2), please refer to the [official setup guide](https://docs.tax.service.gov.uk/mdtp-handbook/documentation/developer-set-up/set-up-service-manager.html) for instructions on how to install and configure it locally.

Once set up:
- To start the service:
  ```sh
  sm2 --start TRADE_REPORTING_EXTRACTS_STUB
  ```
- To stop the service:
  ```sh
  sm2 --stop TRADE_REPORTING_EXTRACTS_STUB
  ```

# API Endpoints Overview

| Name | Method | Endpoint | Description |
|---|---|---|---|
| [Company Information API](#company-information-api) | POST | `/eori/company-information-third-party` | Returns company information for an EORI. |
| [Notification Email API](#notification-email-api) | POST | `/eori/verified-email-third-party` | Returns the verified notification email for an EORI. |
| [EORI History API](#eori-history-api) | POST | `/eori/eori-history-third-party` | Returns EORI history for an EORI. |
| [XI EORI History API](#xi-eori-history-api) | POST | `/eori/gbxi-eori-history-third-party` | Returns EORI history for an EORI using the XI route. |
| [Trader Report Request API](#trader-report-request-api) | PUT | `/gbe/requesttraderreport/v1` | Validates a trader report request and returns `204 No Content` when valid. |
| [Unsupported Trader Report Methods](#unsupported-trader-report-methods) | GET / POST / DELETE | `/gbe/requesttraderreport/v1` | Returns `405 Method Not Allowed` for unsupported methods. |
| [Files Available API](#files-available-api) | GET | `/files-available/list/*informationType` | Returns an SDES-style available files response for an EORI. |
| [Invalid Endpoint API](#invalid-endpoint-api) | GET | `/invalid` | Returns `404 Not Found` for testing. |

---

## Company Information API

**Endpoint**

    POST /eori/company-information-third-party

### Description

Returns company information for the supplied EORI.

The endpoint has specific stub behaviour for EORIs ending in `999` and `998`:

- EORI ending in `999` returns `404 Not Found`.
- EORI ending in `998` returns `404 Not Found`.
- All other EORIs return `200 OK` with company information.

### Request Body

The request body must contain an EORI.

    {
      "eori": "GB1"
    }

### Example Response

    {
      "name": "DEBUG TESTING GB COMPANY (UK) LTD",
      "consent": "1",
      "address": {
        "streetAndNumber": "XYZ Street",
        "city": "ABC City",
        "postalCode": "G11 2ZZ",
        "countryCode": "GB"
      }
    }

### Response Codes

| Status | Description |
|---|---|
| `200 OK` | Company information was found and returned. |
| `404 Not Found` | The EORI ends in `999` or `998`. |

---

## Notification Email API

**Endpoint**

    POST /eori/verified-email-third-party

### Description

Returns the verified notification email for the supplied EORI.

The endpoint has specific stub behaviour for EORIs ending in `999` and `997`:

- EORI ending in `999` returns `404 Not Found`.
- EORI ending in `997` returns `404 Not Found`.
- All other EORIs return `200 OK` with notification email information.

### Request Body

The request body must contain an EORI.

    {
      "eori": "GB123456789012"
    }

### Example Response

    {
      "address": "GB123456789012@company.com",
      "timestamp": "2025-01-01T12:00:00"
    }

### Response Codes

| Status | Description |
|---|---|
| `200 OK` | Notification email information was successfully returned. |
| `404 Not Found` | The EORI ends in `999` or `997`. |

---

## EORI History API

**Endpoint**

    POST /eori/eori-history-third-party

### Description

Returns EORI history for the supplied EORI.

### Request Body

The request body must contain an EORI.

    {
      "eori": "GB123456789012"
    }

### Example Response

    {
      "eoriHistory": [
        {
          "eori": "GB123456789012",
          "validFrom": "2001-01-20",
          "validUntil": "2002-01-20"
        }
      ]
    }

### Response Codes

| Status | Description |
|---|---|
| `200 OK` | EORI history was successfully returned. |

---

## XI EORI History API

**Endpoint**

    POST /eori/gbxi-eori-history-third-party

### Description

Returns EORI history for the supplied EORI using the XI EORI history route.

For this stub, the XI endpoint uses the same `EoriHistoryController.eoriHistory()` action and therefore returns the same response structure as the standard EORI History API.

### Request Body

The request body must contain an EORI.

    {
      "eori": "GB123456789012"
    }

### Example Response

    {
      "eoriHistory": [
        {
          "eori": "GB123456789012",
          "validFrom": "2001-01-20",
          "validUntil": "2002-01-20"
        }
      ]
    }

### Response Codes

| Status | Description |
|---|---|
| `200 OK` | EORI history was successfully returned. |

---

## Trader Report Request API

**Endpoint**

    PUT /gbe/requesttraderreport/v1

### Description

Validates a trader report request.

A request must contain all required headers, a valid EIS authorisation token, and a valid request body.

If all validation succeeds, the stub returns `204 No Content`.

### Request Headers

The following headers are required:

- `accept`
- `authorization`
- `content-type`
- `date`
- `x-correlation-id`
- `x-forwarded-host`

The `authorization` header must contain the configured EIS authorisation token.

### Request Body

The request body must contain a valid trader report request.

Example:

    {
      "endDate": "2024-06-30T23:59:59Z",
      "eori": [
        "GB123456789012",
        "GB123456789013"
      ],
      "eoriRole": "TRADER",
      "reportTypeName": "IMPORTS-ITEM-REPORT",
      "requestID": "RE57965342",
      "requestTimestamp": "2024-06-01T12:00:00Z",
      "requesterEori": "GB123456789012",
      "startDate": "2024-06-01T00:00:00Z"
    }

### Response Codes

| Status | Description |
|---|---|
| `204 No Content` | The request is valid. |
| `400 Bad Request` | One or more required headers are missing, or the request body fails validation. |
| `403 Forbidden` | The authorisation token is invalid. |

### Error Responses

For missing headers, the response contains an error describing the missing header.

For body validation failures, the response contains an error describing the invalid or missing field.

Example header validation error:

    {
      "errorDetail": {
        "errorCode": "400",
        "errorMessage": "Failed header validation",
        "source": "EIS",
        "sourceFaultDetail": {
          "detail": [
            "Failed header validation: Invalid x-forwarded-host header"
          ],
          "restFault": null,
          "soapFault": null
        }
      }
    }

---

## Unsupported Trader Report Methods

**Endpoint**

    GET /gbe/requesttraderreport/v1
    POST /gbe/requesttraderreport/v1
    DELETE /gbe/requesttraderreport/v1

### Description

These methods are not supported by the trader report request endpoint.

Requests using `GET`, `POST`, or `DELETE` return `405 Method Not Allowed`.

### Response Codes

| Status | Description |
|---|---|
| `405 Method Not Allowed` | The HTTP method is not supported for this endpoint. |

---

## Files Available API

**Endpoint**

    GET /files-available/list/*informationType

### Description

Returns an SDES-style available files response for an EORI.

The `informationType` path parameter must match the configured TRE information type.

The endpoint requires valid `x-client-id` and `x-sdes-key` headers.

The EORI is supplied in the `x-sdes-key` header.

### Path Parameters

| Parameter | Description |
|---|---|
| `informationType` | The configured TRE information type. The test configuration uses `TRE`. |

### Request Headers

| Header | Description |
|---|---|
| `x-client-id` | Must match the configured TRE client ID. |
| `x-sdes-key` | The EORI used to populate the available files response. |

### Example Request

    GET /files-available/list/TRE
    x-client-id: TRE-CLIENT-ID
    x-sdes-key: GB123456789012

The request body is not used by the stub.

### Example Response

The response is a JSON array containing one available file:

    [
      {
        "filename": "test-2018-06.csv",
        "downloadURL": "https://assets.publishing.service.gov.uk/media/699ed0cbdb2401de164d6cdb/Example_imports_item_report.csv",
        "fileSize": 1234,
        "metadata": [
          {
            "metadata": "FileCreationTimestamp",
            "value": "30"
          },
          {
            "metadata": "FileType",
            "value": "CSV"
          },
          {
            "metadata": "EORI",
            "value": "GB123456789012"
          },
          {
            "metadata": "MdtpReportXCorrelationId",
            "value": "2409398b-ee8f-47cd-b873-ac7ac099c28b"
          },
          {
            "metadata": "MdtpReportRequestId",
            "value": ""
          },
          {
            "metadata": "MdtpReportTypeName",
            "value": "Imports-Header"
          },
          {
            "metadata": "ReportFileCounter",
            "value": "1of1"
          },
          {
            "metadata": "ReportLastFile",
            "value": "false"
          }
        ]
      }
    ]

The stub populates the response dynamically using:

| Template value | Stub value |
|---|---|
| `{{EORI_VALUE}}` | Value of the `x-sdes-key` header |
| `{{REPORT_ID}}` | Empty string |
| `{{PART_NUM}}` | `1` |
| `{{TOTAL_PARTS}}` | `1` |

Therefore, the generated `ReportFileCounter` is always `1of1` and `MdtpReportRequestId` is an empty string.

### Response Codes

| Status | Description |
|---|---|
| `200 OK` | The request is valid and available file information is returned. |
| `400 Bad Request` | The `x-client-id` or `x-sdes-key` header is missing. |
| `403 Forbidden` | The `x-client-id` is invalid or the `informationType` is invalid. |

### Error Responses

Missing `x-client-id`:

    Missing x-client-id header

Invalid `x-client-id`:

    Invalid x-client-id header

Invalid information type:

    Invalid information type

Missing `x-sdes-key`:

    Missing x-sdes-key header

---

## Invalid Endpoint API

**Endpoint**

    GET /invalid

### Description

Returns a `404 Not Found` response for testing.

### Response Codes

| Status | Description |
|---|---|
| `404 Not Found` | The requested endpoint does not exist. |

---

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").

