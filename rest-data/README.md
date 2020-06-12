# Sample Spring Data Rest

We have a couple of tables that we want to easily expose, via a REST API, with limited effort.

The solution - Spring Data Rest (and Spring Doc Open API to document it)

This shows how to build a basic, HAL aware, REST API around some entities.

It also shows how to use projections to enahnce/alter the responses.

### Limit default API 

See SpringDataRestConfig where we limit the exposed API to methods we define, disableDefaultExposure, 
and @RepositoryRestResource/@RestResource in the repository interfaces.

### ID Fields

See SpringDataRestConfig where we expose the Id fields for the Route and Stop entities.

### Projections  

See RouteProjection and StopProjection for how we can expose projections over and above the raw data.

The StopProjection also includes simple conversions of the data using SpEL.

## Some example URLs to try out

### Swagger UI

http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/

### Retrieve some routes

http://localhost:8080/routes/search/findAllByDateTimeBetween?from=2020-01-01T00%3A00&until=2021-01-01T00%3A00

### Retrieve some stops

http://localhost:8080/stops/search/findAllByRouteId?id=2

### Retrieve some stops using a projection

http://localhost:8080/stops/search/findAllByRouteId?id=2&projection=stop