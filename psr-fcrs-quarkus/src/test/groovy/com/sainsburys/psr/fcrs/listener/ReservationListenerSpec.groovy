package com.sainsburys.psr.fcrs.listener

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.sainsburys.psr.fcrs.dto.ReservedOrder
import com.sainsburys.psr.fcrs.service.ReservationService
import spock.lang.Specification

class ReservationListenerSpec extends Specification {
    public static final String RESERVATION = "reservation"
    public static final String RELEASE = "release"
    ReservationService reservationService = Mock(ReservationService)
    ObjectMapper objectMapper = Mock(ObjectMapper)
    ReservationListener reservationListener = new ReservationListener(reservationService, objectMapper)

    def "When a order reservation arrives we call the reservation service" () {

        given: "An order reservation"
        String reservedOrderString = "{\"orderNumber\": \"1234\"}"

        and: "A reserved order"
        ReservedOrder reservedOrder = Mock(ReservedOrder)

        and: "the type is reservation"
        reservedOrder.getType() >> RESERVATION

        and: "the object mapper returns a valid reservedOrder"
        objectMapper.readValue(reservedOrderString, ReservedOrder) >> reservedOrder

        when: "An order reservation arrives"
        reservationListener.listen(reservedOrderString)

        then: "The service is called"
        1 * reservationService.addOrderToRoute(reservedOrder)

    }

    def "When a order with a wrong type arrives we throw the exception" () {

        given: "An order reservation"
        String reservedOrderString = "{\"orderNumber\": \"1234\"}"

        and: "A reserved order"
        ReservedOrder reservedOrder = Mock(ReservedOrder)

        and: "the type is reservation"
        reservedOrder.getType() >> "not known type"

        and: "the object mapper returns a valid reservedOrder"
        objectMapper.readValue(reservedOrderString, ReservedOrder) >> reservedOrder

        when: "An order reservation arrives"
        reservationListener.listen(reservedOrderString)

        then: "The service is not called"
        0 * reservationService.addOrderToRoute(reservedOrder)

    }

    def "When a order reservation arrives we throw the exception" () {

        given: "A wrong order reservation"
        String reservedOrderString = "{\"ordeblaaaah"

        and: "the object mapper throws an exception"
        objectMapper.readValue(reservedOrderString, ReservedOrder) >> {
            throw Mock(JsonProcessingException)
        }

        when: "An order reservation arrives"
        reservationListener.listen(reservedOrderString)

        then: "The illegal argument exception is thrown"
        thrown(IllegalArgumentException)

    }

    def "When a release message is received, the reservation service is called to handle it"(){

        given: "An order reservation"
        String reservedOrderString = "{\"orderNumber\": \"1234\"}"

        and: "A reserved order"
        ReservedOrder reservedOrder = Mock(ReservedOrder)

        and: "the type is release"
        reservedOrder.getType() >> RELEASE

        and: "the object mapper returns a valid reservedOrder"
        objectMapper.readValue(reservedOrderString, ReservedOrder) >> reservedOrder

        when: "An order reservation arrives"
        reservationListener.listen(reservedOrderString)

        then: "the service is called"
        1 * reservationService.removeOrderFromRoute(reservedOrder)

    }
}
