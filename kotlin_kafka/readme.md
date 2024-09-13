# Sample Spring/Kafka in Kotlin

## About

Very simple Kafka demo.

Reads messages from the command line, then publishes them to Kafka and reads them back later on.

Auto configures the topic for the demo within the main class.

Also includes a simple demonstratino of the Spring Cloud Streaming support

## Building

1. Use gradle/IntelliJ

## Running

1. Spin up docker compose which will start kafka (and zookeeper and an admin UI)
2. Run the application (via gradle or in the IDE)

## UI

Hit http://localhost:8080 for a simple UI to monitor/administer the local Kafka
