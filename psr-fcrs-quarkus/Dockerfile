FROM adoptopenjdk/openjdk11:latest
COPY target/application.jar /home/app.jar
COPY target/newrelic/newrelic.jar /home/newrelic.jar
CMD ["java","-javaagent:/home/newrelic.jar","-jar","/home/app.jar"]
