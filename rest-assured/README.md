# Rest Assured and Spock

Simple project showing the usage of Rest Assured with Spock, with a comparison to a Java test.

For the case in point it's is used with a Spring Boot application.

There area couple of Spock tests, once using the Rest Assured mock and one spinning up an full fat integration test with
a running copy of Spring.

The Java test, used for comparison, just spins up the integration test.

## Structure

This is a simple Spring Boot application which has a service to add together 2 numbers.

It uses Gradle, and Java 11, to build and execute.

It's the tests that are an attemot to show what the 2 technologies being looked at, RestAssured and Spock, can do and
how they can be used together, along with a simple comparison to a raw Java unit test using the Rest Assured DSL.

## Conclusion

Does Rest Assured provide anything additional over Spock and, for out application concept, Mock MVC?

The answer is probably not.

whilst it is a nice DSL for Java, the give/when/then being built in, it does not naturally fit with Spock's existing DSL
given:/when:/then: structure.

Whilst there are advantages, such as the rich support for JSON response validation, and other assertions, the advantages
are not really that great over and above using Spock with MockMvc and/or TestRestTemplate. 