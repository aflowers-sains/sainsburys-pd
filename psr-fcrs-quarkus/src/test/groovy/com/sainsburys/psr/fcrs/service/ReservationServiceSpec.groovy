package com.sainsburys.psr.fcrs.service

import com.sainsburys.psr.fcrs.data.RouteItem
import com.sainsburys.psr.fcrs.dto.ReservedOrder
import com.sainsburys.psr.fcrs.repository.RouteItemRepository
import com.sainsburys.psr.fcrs.service.flrs.dto.FulfilledBy
import org.springframework.dao.DataIntegrityViolationException
import spock.lang.Specification

import java.time.LocalDateTime

class ReservationServiceSpec extends Specification {

    private RouteItemRepository routeItemRepository = Mock(RouteItemRepository)
    FlrsService flrsService = Mock(FlrsService)
    ReservationService reservationService = new ReservationService(routeItemRepository, flrsService)

    def FLRS_ID = "Q0xJQ0tfQU5EX0NPTExFQ1Q6ZGJmNjM4ODItODQ1ZS00MmExLWFiM2UtMjVkZWEyMmE1ODFm"
    def STORE_ID = "2320"
    def STORE_NAME = "Herne Bay"

    def "It saves a route and order if route does not yet exist"() {
        given: "A reserved order"
        def startDateTime = LocalDateTime.now()
        def endDateTime = startDateTime.plusHours(1)
        def reservedOrder = ReservedOrder.builder()
                .flrsId(FLRS_ID)
                .orderNumber("chicken")
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build()

        and: "a flrs response"
        def fulfilledBy = new FulfilledBy(STORE_ID, STORE_NAME)

        when: "we call the service"
        reservationService.addOrderToRoute(reservedOrder)

        then: "flrs service is called"
        1 * flrsService.getFullfilledByForFlrsId(FLRS_ID) >> Optional.of(fulfilledBy)

        and: "The route is created"
        1 * routeItemRepository.save({ RouteItem it ->
            it.getStoreId() == STORE_ID
            it.getFlrsId() == FLRS_ID
            it.getStoreName() == STORE_NAME
            it.getOrderNumber() == "chicken"
            it.getStartDateTime() == startDateTime
            it.getEndDateTime() == endDateTime
        })
    }

    def "If the route already exists, and the order number already exists for that route, don't do anything"() {

        given: "A reserved order"
        def reservedOrder = Mock(ReservedOrder)

        and: "flrs can find the store id"
        def fulfilledBy = new FulfilledBy(STORE_ID, STORE_NAME)
        flrsService.getFullfilledByForFlrsId(_) >> Optional.of(fulfilledBy)

        and: "it is in repository"
        1 * routeItemRepository.save(_) >> { throw new DataIntegrityViolationException("exception") }

        when: "the service is called"
        reservationService.addOrderToRoute(reservedOrder)

        then: "no new route is created"
        noExceptionThrown()
    }

    def "Service releases reservation when the message arrives"() {

        given: "A reserved order"
        def startDateTime = LocalDateTime.now()
        def endDateTime = startDateTime.plusHours(1)
        def reservedOrder = ReservedOrder.builder()
                .flrsId(FLRS_ID)
                .orderNumber("chicken")
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build()

        when: "service tries to remove the reservation"
        reservationService.removeOrderFromRoute(reservedOrder)

        then: "the reservation is removed"
        1 * routeItemRepository.deleteAllByOrderNumberAndFlrsIdAndStartDateTime("chicken", FLRS_ID, startDateTime) >> 1


    }

    def "Exception thrown when service called to remove reservation that does not exist"() {

        given: "A reserved order"
        def reservedOrder = Mock(ReservedOrder)

        and: "there is no entity to be deleted found in the repository"
        routeItemRepository.deleteAllByOrderNumberAndFlrsIdAndStartDateTime(_, _, _) >> 0

        when: "service tries to remove the reservation"
        reservationService.removeOrderFromRoute(reservedOrder)

        then: "an exception is thrown"
        thrown(IllegalArgumentException)
    }

}
