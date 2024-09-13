# Spring Boot Sampler

Simple application of some simple spring Boot Micro Services.

A thought for how we could do things in a REST/Messaging/Service way.

It uses SQS to send point to point messages of updates that services need to know about.

It also uses Kafka, for more persistent, broadcast, messages/events.

## Some salient points

It's about contract based development, so we don't share code between services, we read and understand the contract and build out clients for that.

The 'team' who maintains the service decides on the technology for the service, and how to achieve the goals set out for that service. 

Would be nice to use PACT for the REST side, but that's for another day.

It's all slammed into a single mono-repo; this is not the way to do it. It should be in separate repositories, but for ease of use it as it is.

## Running

It's Java 21 so you'll need to adjust to have that execute everything.

Run up the docker compose file. This will start kafka and localstack (which provides local sqs, s3, dynamodb etc) and postgres

In the root, build everything first with ```./gradlew clean build```

Then we can spin it all up with ```./gradlew bootRun```

This will start all the applications on a specific set of ports,so we can then try out the REST apis.

### Polyglot

To demonstrate using the 'best' technology for the solution, a couple of the projects are not using Java.

One is a Kotlin application and another is Python.

#### Kotlin

To demonstrate using the JVM but with a different language, we have a dbaudit project which utilises Kotlin.

This is a simple listener for Kafka events/messages with an associated data store for these messages, 
alongside a simple controller to allow retrieval.

It demonstrates w can use different languages for the JVM, especially if they prove better than raw Java.

#### Python

There's a python app in here, in pythonaudit, that listens to Kafka messages and 'audits' them.

For now, that is just a console log, but it indicates how we can use different technologies to achieve goals.

### Service Registry 

We use a service registry here to simplify how the disparate services communicate with each other.

This can be seen at http://localhost:8761/.

For example clients using a feign client to automagically connect to the parameters service.

### Ports

| Application      | Port |
|------------------|------|
| clients          | 8666 |
| parameters       | 8667 |  
| policies         | 8668 |  
| quotes           | 8669 |   
| db audit         | 8670 |   
| service registry | 8761 |  

### Example requests

i.e.

```curl http://localhost:8667/sainsburys/parameters/``` to get the current parameters

```curl -X POST http://localhost:8667/sainsburys/parameters/ -H "Content-Type: application/json" -d "{\"name\": \"p4\", \"value\": \"4\"}"``` to get the current parameters

The post of parameters will trigger an SQS message to the quotes application about the update.\
It will also trigger a Kafka message to all interested parties.

## TODO

Add better quality APIs\
Have simple service to service calls\
Have an API gateway to merge back end data into monolithic blobs, ask for client, then get policies etc etc\
Add nodejs service\
Add UI and micro front ends\
Sort out the start ordering that is required\
Introduce logstash logging and ELK\