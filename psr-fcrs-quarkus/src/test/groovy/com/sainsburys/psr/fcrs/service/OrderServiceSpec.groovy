package com.sainsburys.psr.fcrs.service

import com.sainsburys.psr.fcrs.data.RouteItem
import com.sainsburys.psr.fcrs.repository.RouteItemRepository
import org.hibernate.exception.JDBCConnectionException
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class OrderServiceSpec extends Specification {

    RouteItemRepository routeItemRepository = Mock(RouteItemRepository)

    def orderService = new OrderService(routeItemRepository)


    def "Retrieves information for a list of order numbers"() {
        given: "a list of order numbers"
        def orderNumbers = ["o1", "o2"]

        when: "service is asked to retrieve information"
        def orderInfo = orderService.orderInformation(orderNumbers)

        then: "the route item repository is called"
        1 * routeItemRepository.findAllByOrderNumberIn(orderNumbers) >> List.of(
                RouteItem.builder().orderNumber("o1").startDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0))).build(),
                RouteItem.builder().orderNumber("o2").startDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0))).build()
        )

        and: "list of order information is returned"
        orderInfo.size() == 2
        orderInfo.get(0).orderNumber == "o1"
        orderInfo.get(0).dropInformation.storeDepartureDateTime == LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0))
        orderInfo.get(0).dropInformation.lane == "CC"
        orderInfo.get(0).dropInformation.vanNumber == "81"
        orderInfo.get(0).dropInformation.dropNumber == 1
        orderInfo.get(0).dropInformation.arrivalDateTime ==  LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0))

        orderInfo.get(1).orderNumber == "o2"
        orderInfo.get(1).dropInformation.storeDepartureDateTime == LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0))
        orderInfo.get(1).dropInformation.lane == "CC"
        orderInfo.get(1).dropInformation.vanNumber == "81"
        orderInfo.get(1).dropInformation.dropNumber == 2
        orderInfo.get(1).dropInformation.arrivalDateTime ==  LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0))

    }

    def "it returns empty list if there are no route items"() {
        given: "there are no route items"
        routeItemRepository.findAllByOrderNumberIn(_) >> []

        when: "service is asked to retrieve information"
        def orderInfo = orderService.orderInformation(["o1", "o2"])

        then: "an empty list is returned"
        orderInfo.size() == 0
    }

    def "it propagates exception if there are db issues"() {
        given: "DB is having a bad day"
        routeItemRepository.findAllByOrderNumberIn(_) >> {throw new JDBCConnectionException("Chicken", null)}

        when: "service is asked to retrieve information"
        orderService.orderInformation(["o1", "o2"])

        then: "the exception is thrown"
        def ex = thrown(JDBCConnectionException)
        ex.getMessage() == "Chicken"
    }
}
