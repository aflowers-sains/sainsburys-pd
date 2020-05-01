# Mutation Testing and Spock

Simple project showing the usage of PIT Mutation Testing with Spock, with a comparison to a Java test.

For the case in point it's used with a Spring Boot application.

## Structure

This is a simple Spring Boot application which has a service to add together 2 numbers.

It uses Gradle, and Java 11, to build and execute.

It's the tests that are an attemot to show what the 2 technologies being looked at, PIT and Spock, can do and
how they can be used together, along with a simple comparison to a raw Java unit test.

## Conclusion

Does PIT provide anything, especially with Spock?

The answer is YES. It will allow validation that we are testing more cases in our tests.

As an example it is, by default, set to run a test that will pass regardless of the result from the called service.

This test fails for PIT mutation testing as it always passes regardless of the changes the PIT mutations apply, hence
we have missing test cases.

If we comment out the excluded tests in build.gradle we have tests that cover all scenarios and will pass PIT testing
as mutations of the source will be caught in tests.

