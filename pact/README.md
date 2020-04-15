# PACT

Simple project showing the usage of PACT.

For the case in point it is used with a Spring Boot application.

## Structure

This is a simple Spring Boot application which has a producer service to add together 2 numbers.

It uses Gradle, and Java 11, to build and execute.

If also has a consumer that is where the bulk of the PACT work lives.

## Building

Just run the build task in the client project, and this will generate the json for the PACT.

Then running the PACT verify task in the server project will ensure that the API conforms to the consumer
requirements/expectations.

## Conclusion

PACT is cool, and is likely of use given we tend to create services for which we are the consumers in the main.

For services that will be accessed outside of the PSR space, i.e. via GOL, then we can start conversations to ensure
we have good contracts in place that satisfy what the consumer actually wants.