# weatherService
Three day aggregation service for weather condition, backed by openweathermap api

API documentation can be obtained from [here](https://github.com/muratguvenc/weatherService/tree/master/src/main/resources/swagger) via `weatherService.yaml` file

Service contains two APIs

### Forecast retrieval service

Curl : 
``` curl -X GET "http://localhost:8080/weather/Adana/forecast" -H "accept: application/json" ```

Request Url :
``` http://localhost:8080/weather/Adana/forecast ```

### Cache clear service

Curl : 
``` curl -X DELETE "http://localhost:8080/weather/cache" -H "accept: application/json" ```

Request url : 
``` http://localhost:8080/weather/cache ```

### Used libraries
- For Api interface generation swagger-codegen,
- For Build automation maven,
- To reduce boiler plate code, 
   - ModelMapper
   - Apache Commons
   - Project Lombok
- For Caching, spring boot simple caching,
- For Rest client, Feign Client

### To build and run application

- ``` mvn clean package ``` 
- ``` mvn spring-boot:run ```

final note : openweathermap api key found in application properties is not an active key, 
please use an active key to see service running.
