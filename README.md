## Overview
This service allows to
- create a device
- get specific device by id
- get all devices
- get all devices by brand
- update a device partially or fully by device and/or brand name
- delete an existing device

## Details
This a Spring Boot application that runs an HTTP server on top of In-Memory database.
The [schema.sql](src/main/resources/schema.sql) file contains the schema for database tables.
The [data.sql](src/main/resources/data.sql) file contains an initial entry into the database table for health check

### API
-  Each API endpoint is specified in the [OpenAPI spec](openapi.yaml) and allows you to test the endpoints directly

### SetUp
- To run the application, just run the command `mvn spring-boot:run`
- To run tests for Device controller, run the command `mvn test -Dtest="DeviceControllerTest"`

## Assumptions
There has been some assumptions made to keep the context of project simple.
The user can create device with same details multiple times and there is no restriction here. Any changes to device name 
and brand are permitted.

## Improvements and TODOs
- Ideally we should get a unique identifier for every device like IMEI address, so we can distinguish
  between devices with same device and brand name.
- Error handling can be better and more tailored depending on the API agreement with the clients
- An index on brand in devices table has been introduced to cater for huge amount of data in future and still allow 
search by brand
- For timestamps, _INSTANT_ type is used to keep everything simple and test easily. For broader scale, _ZONEDDATETIME_ would
  work better as it will explicitly cater in zones