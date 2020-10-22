package com.sainsburys.psr.fcrs.integration

import ch.qos.logback.classic.Level
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.sainsburys.psr.fcrs.IntegrationTestBase
import com.sainsburys.psr.fcrs.listener.ReservationListener
import com.sainsburys.psr.fcrs.repository.RouteItemRepository
import com.sainsburys.psr.fcrs.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jms.core.JmsTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import spock.lang.Unroll

import java.time.LocalDateTime

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson
import static com.github.tomakehurst.wiremock.client.WireMock.post
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import static org.awaitility.Awaitility.await
import static java.util.concurrent.TimeUnit.SECONDS

class ReservationListenerSpecIT extends IntegrationTestBase {
    final static String ORDER_NUMBER = "888888888"
    final static String FLRS_ID = "Q0xJQ0tfQU5EX0NPTExFQ1Q6ZGJmNjM4ODItODQ1ZS00MmExLWFiM2UtMjVkZWEyMmE1ODFm"
    final static String CACHED_FLRS_ID = "cached"
    final static String START_DATE_TIME = "2020-04-01T13:00:00"
    final static String DESTINATION = "psr.fcss.v1.orders.reserved"

    WireMockServer wireMockServer = new WireMockServer(wireMockConfig()
            .port(8089)
            .notifier(new ConsoleNotifier(true)))

    static GenericContainer<?> activeMQContainer

    def setupSpec() {
        activeMQContainer = new GenericContainer<>("rmohr/activemq:latest").withExposedPorts(61616)
        activeMQContainer.start()
    }

    def cleanupSpec() {
        activeMQContainer.stop()
    }

    def setup() {
        routeItemRepository.deleteAll()

        jmsTemplate.setReceiveTimeout(100)
        while (jmsTemplate.receive("ActiveMQ.DLQ") != null) {
        }

        wireMockServer.start()
        mockFlrsResponse()
    }

    def cleanup() {
        wireMockServer.resetAll()
        wireMockServer.stop()
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.activemq.broker-url", { -> "tcp://localhost:" + activeMQContainer.getMappedPort(61616) })
    }

    @Autowired
    JmsTemplate jmsTemplate

    @Autowired
    RouteItemRepository routeItemRepository

    def logger

    def "It reads a message from the bus indicating an order has been reserved"() {
        given: "a reserved order string"
        String reservedOrder = """{
            "orderNumber": "$ORDER_NUMBER",
            "flrsId": "$FLRS_ID",
            "startDateTime": "2020-04-01T13:00:00",
            "endDateTime": "2020-04-01T14:00:00",
            "type": "reservation"
        }"""

        and: "set up logger"
        logger = setupLogAppender(ReservationService)

        when: "a message is placed onto the bus"
        jmsTemplate.convertAndSend(DESTINATION, reservedOrder)

        and: "we wait"
        await().atMost(5, SECONDS).until({ -> routeItemRepository.findAll().size() > 0 })

        then: "database is updated"
        def routeItemsInDB = routeItemRepository.findAll()
        routeItemsInDB.size() == 1
        routeItemsInDB.get(0).storeId == "2320"
        routeItemsInDB.get(0).flrsId == FLRS_ID
        routeItemsInDB.get(0).orderNumber == "$ORDER_NUMBER"
        routeItemsInDB.get(0).startDateTime == LocalDateTime.of(2020, 4, 1, 13, 0, 0)
        routeItemsInDB.get(0).endDateTime == LocalDateTime.of(2020, 4, 1, 14, 0, 0)

    }

    def "It saves order for a specific route only once"() {

        given: "set up logger"
        logger = setupLogAppender(ReservationService)

        and: "a reserved order"
        String reservedOrder = """{
            "orderNumber": "TOMATO",
            "flrsId": "$FLRS_ID",
            "startDateTime": "2020-04-01T13:00:00",
            "endDateTime": "2020-04-01T14:00:00",
            "type": "reservation"
        }"""

        when: "a message is placed onto the bus"
        jmsTemplate.convertAndSend(DESTINATION, reservedOrder)
        jmsTemplate.convertAndSend(DESTINATION, reservedOrder)

        then: "the message appears on the DLQ"
        def dlqMessage
        jmsTemplate.setReceiveTimeout(5000)
        await().atMost(5, SECONDS).until({ -> (dlqMessage = jmsTemplate.receive("ActiveMQ.DLQ")) != null })

        and: "the order number is logged"
        def logs = filterLogItems(logger, Level.INFO)
        logs.size() == 3
        logs.first().getMessage() == "Duplicate order number detected in a route, will be ignored"
        logs.first().getArgumentArray().toString() == "[orderNumber=TOMATO]"

        and: "database has only saved a single record"
        def routeItemsInDB = routeItemRepository.findAll()
        routeItemsInDB.size() == 1
    }

    def "Application releases reservation when release message arrives"() {

        given: "a reserved order string"
        String reservedOrder = """{
            "orderNumber": "$ORDER_NUMBER",
            "flrsId": "$FLRS_ID",
            "startDateTime": "2020-04-01T13:00:00",
            "endDateTime": "2020-04-01T14:00:00",
            "type": "reservation"
        }"""

        and: "a message is placed onto the bus"
        jmsTemplate.convertAndSend(DESTINATION, reservedOrder)

        and: "we wait"
        await().atMost(5, SECONDS).until({ -> routeItemRepository.findAll().size() > 0 })

        and: "a released order string"
        String releasedOrder = """{
            "orderNumber": "$ORDER_NUMBER",
            "flrsId": "$FLRS_ID",
            "startDateTime": "2020-04-01T13:00:00",
            "endDateTime": "2020-04-01T14:00:00",
            "type": "release"
        }"""

        when: "a message is placed onto the bus"
        jmsTemplate.convertAndSend(DESTINATION, releasedOrder)

        then: "the repository is empty"
        await().atMost(5, SECONDS).until({ -> routeItemRepository.findAll().size() == 0 })
        routeItemRepository.findAll().size() == 0
    }

    @Unroll
    def "Application releases expected reservations"() {

        given: "a reserved order string"
        String reservedOrder1 = """{
            "orderNumber": "$ORDER_NUMBER",
            "flrsId": "$FLRS_ID",
            "startDateTime": "$START_DATE_TIME",
            "endDateTime": "2020-04-01T14:00:00",
            "type": "reservation"
        }"""

        and: "another reserved order string"
        String reservedOrder2 = """{
            "orderNumber": "$order_number",
            "flrsId": "$flrs_id",
            "startDateTime": "$start_date_time",
            "endDateTime": "2020-04-01T14:00:00",
            "type": "reservation"
        }"""

        and: "a messages are placed in the bus"
        jmsTemplate.convertAndSend(DESTINATION, reservedOrder1)
        jmsTemplate.convertAndSend(DESTINATION, reservedOrder2)

        and: "we wait"
        await().atMost(5, SECONDS).until({ -> routeItemRepository.findAll().size() >= initial_expectation })

        and: "the message appears on the DLQ"
        if (expectedOnDlq) {
            def dlqMessage
            jmsTemplate.setReceiveTimeout(5000)
            await().atMost(5, SECONDS).until({ -> (dlqMessage = jmsTemplate.receive("ActiveMQ.DLQ")) != null })
        }

        and: "a released order string"
        String releasedOrder = """{
            "orderNumber": "$ORDER_NUMBER",
            "flrsId": "$FLRS_ID",
            "startDateTime": "$START_DATE_TIME",
            "endDateTime": "2020-04-01T14:00:00",
            "type": "release"
        }"""

        when: "a message is placed onto the bus"
        jmsTemplate.convertAndSend(DESTINATION, releasedOrder)

        then: "the repository still contains one reservation"
        await().atMost(5, SECONDS).until({ -> routeItemRepository.findAll().size() >= final_expectation })
        routeItemRepository.findAll().size() == final_expectation

        where:
        order_number | flrs_id | start_date_time       | initial_expectation | final_expectation | expectedOnDlq
        ORDER_NUMBER | FLRS_ID | START_DATE_TIME       | 1                   | 0                 | true
        ORDER_NUMBER | "diff"  | START_DATE_TIME       | 2                   | 1                 | false
        ORDER_NUMBER | FLRS_ID | "2020-04-01T10:00:00" | 2                   | 1                 | false
        ORDER_NUMBER | "diff"  | "2020-04-01T10:00:00" | 2                   | 1                 | false
        "diff"       | FLRS_ID | START_DATE_TIME       | 2                   | 1                 | false


    }

    def "FLRS IDs are cached"() {

        given: "a reserved order string"
        String reservedOrder1 = """{
            "orderNumber": "1",
            "flrsId": "$CACHED_FLRS_ID",
            "startDateTime": "$START_DATE_TIME",
            "endDateTime": "2020-04-01T14:00:00",
            "type": "reservation"
        }"""

        and: "another reserved order string"
        String reservedOrder2 = """{
            "orderNumber": "2",
            "flrsId": "$CACHED_FLRS_ID",
            "startDateTime": "$START_DATE_TIME",
            "endDateTime": "2020-04-01T14:00:00",
            "type": "reservation"
        }"""

        and: "A FLRS request body"
        String cachedRequestBody = this.getClass().getResource("/flrs/cached_request.json").text

        when: "a messages are placed in the bus"
        jmsTemplate.convertAndSend(DESTINATION, reservedOrder1)

        and: "we wait"
        await().atMost(5, SECONDS).until({ -> routeItemRepository.findAll().size() >= 1 })

        and: "another message is placed in the bus"
        jmsTemplate.convertAndSend(DESTINATION, reservedOrder2)

        and: "we wait"
        await().atMost(5, SECONDS).until({ -> routeItemRepository.findAll().size() >= 2 })

        then: "FLRS was only called once"
        wireMockServer.verify(1, postRequestedFor(urlEqualTo("/graphql")).withRequestBody(equalToJson(cachedRequestBody)))

    }

    def "Release messages are sent to the DLQ if reservation not found"() {
        given: "a released order string"
        String releasedOrder = """{
            "orderNumber": "$ORDER_NUMBER",
            "flrsId": "$FLRS_ID",
            "startDateTime": "2020-04-01T13:00:00",
            "endDateTime": "2020-04-01T14:00:00",
            "type": "release"
        }"""

        and: "set up logger"
        logger = setupLogAppender(ReservationService)

        when: "a message is placed onto the bus"
        jmsTemplate.convertAndSend(DESTINATION, releasedOrder)

        then: "the message appears on the DLQ"
        def dlqMessage
        jmsTemplate.setReceiveTimeout(5000)
        await().atMost(5, SECONDS).until({ -> (dlqMessage = jmsTemplate.receive("ActiveMQ.DLQ")) != null })
        dlqMessage != null

        and: "the message was retried twice"
        def logs = filterLogItems(logger, Level.WARN, "Reservation to release not found")
        logs.size() == 3
        logs.first().getArgumentArray().toString() == "[orderNumber=$ORDER_NUMBER]"

    }

    def "Throws exception when input is no good"() {
        given: "a reserved order string"
        String reservedOrder = "{\"noooo"

        and: "set up logger"
        logger = setupLogAppender(ReservationListener)

        when: "a message is placed onto the bus"
        jmsTemplate.convertAndSend(DESTINATION, reservedOrder)

        and: "We wait"
        await().atMost(5, SECONDS).until({ -> filterLogMessages(logger, Level.ERROR).size() >= 1 })

        then: "the order number is logged"
        def logs = filterLogItems(logger, Level.ERROR)
        logs.size() == 1
        logs.first().getMessage() == "Failed to convert the following message"
        logs.first().getArgumentArray().toString() == "[message=$reservedOrder]"

        and: "database is not updated"
        def routeItemsInDB = routeItemRepository.findAll()
        routeItemsInDB.size() == 0
    }

    def mockFlrsResponse() {
        def expectedRequest = this.getClass().getResource("/flrs/request.json").text
        def expectedCachedRequest = this.getClass().getResource("/flrs/cached_request.json").text
        def expectedRequestForDiff = this.getClass().getResource("/flrs/request_diff.json").text
        def response = this.getClass().getResource("/flrs/response.json").text

        wireMockServer.stubFor(post(urlEqualTo("/graphql"))
                .withRequestBody(equalToJson(expectedRequest))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withBody(response)
                        .withStatus(200)))

        wireMockServer.stubFor(post(urlEqualTo("/graphql"))
                .withRequestBody(equalToJson(expectedCachedRequest))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withBody(response)
                        .withStatus(200)))

        wireMockServer.stubFor(post(urlEqualTo("/graphql"))
                .withRequestBody(equalToJson(expectedRequestForDiff))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                        .withBody(response)
                        .withStatus(200)))
    }
}
