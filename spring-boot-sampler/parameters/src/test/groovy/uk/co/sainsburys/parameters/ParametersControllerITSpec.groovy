package uk.co.sainsburys.parameters

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = ["eureka.client.enabled:false"])
class ParametersControllerITSpec extends Specification {
    @Shared
    static LocalStackContainer localStackContainer = new LocalStackContainer(DockerImageName.parse('localstack/localstack:2.2.0'))
            .withServices(LocalStackContainer.Service.DYNAMODB, LocalStackContainer.Service.SQS).withEnv("AWS_DEFAULT_REGION", "eu-west-1").withEnv("DEFAULT_REGION", "eu-west-1")

    @Shared
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.2")).withExposedPorts(9092, 9093)

    @Autowired
    TestRestTemplate testRestTemplate

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        println("Region == ${localStackContainer.region}")
        registry.add("spring.cloud.aws.region.static", () -> localStackContainer.getRegion())
        registry.add("spring.cloud.aws.credentials.access-key", () -> localStackContainer.getAccessKey())
        registry.add("spring.cloud.aws.credentials.secret-key", () -> localStackContainer.getSecretKey())
        registry.add(
                "spring.cloud.aws.dynamodb.endpoint",
                () -> localStackContainer.getEndpointOverride(LocalStackContainer.Service.DYNAMODB).toString())
        registry.add(
                "spring.cloud.aws.sqs.endpoint",
                () -> localStackContainer.getEndpointOverride(LocalStackContainer.Service.SQS).toString())

        registry.add(
                "spring.kafka.bootstrap-servers",
                () -> kafkaContainer.getBootstrapServers())
    }

    def setupSpec() {
        localStackContainer.start()
        localStackContainer.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", "sainsburys-parameters")

        kafkaContainer.start()
    }

    def "Calling the REST API to add a parameter saves to database and notifies interested parties"() {
        given:
        def parameter = new Parameter("param_name", "param_value")

        when:
        def response = testRestTemplate.postForEntity("/sainsburys/parameters/", parameter, String.class)

        then:
        response.statusCode.is2xxSuccessful()
        response.body == "added"
    }
}

