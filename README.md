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

## API Endpoints

| Endpoint                                         | Description                                      | Request Body Example / Schema         | Response Body Example / Schema           |
|--------------------------------------------------|--------------------------------------------------|---------------------------------------|------------------------------------------|
| POST /eori/company-information-third-party        | Simulate company information lookup              | `{ "eori": "GB123456789000" }`     | `CompanyInformation` JSON                |
| POST /eori/verified-email-third-party             | Simulate notification email lookup               | `{ "eori": "GB123456789000" }`     | `{ "email": "test@example.com" }`     |
| POST /eori/eori-history-third-party               | Simulate EORI history lookup                     | `{ "eori": "GB123456789000" }`     | See `EoriHistoricalData.json`            |
| PUT /gbe/requesttraderreport/v1                  | Simulate trader report request                   | See `API1Request.json`                | 202/204/400/500 (empty or error JSON)    |
| GET /gbe/requesttraderreport/v1                  | Simulate trader report retrieval (other methods) | N/A                                   | Report JSON or error JSON                |
| POST /gbe/requesttraderreport/v1                 | Simulate trader report (other methods)           | See `API1Request.json`                | Report JSON or error JSON                |
| DELETE /gbe/requesttraderreport/v1               | Simulate trader report deletion (other methods)  | N/A                                   | 204/404/500 (empty or error JSON)        |
| GET /invalid                                     | Simulate invalid endpoint                        | N/A                                   | Error JSON                              |
| GET /files-available/list/*informationType        | Simulate files available listing                 | N/A                                   | See `SdesAvialableFiles.json`            |

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").

