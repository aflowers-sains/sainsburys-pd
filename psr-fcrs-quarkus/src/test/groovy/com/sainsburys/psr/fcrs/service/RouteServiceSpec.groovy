package com.sainsburys.psr.fcrs.service

import com.sainsburys.psr.fcrs.data.RouteItem
import com.sainsburys.psr.fcrs.repository.RouteItemRepository
import com.sainsburys.psr.fcrs.rest.api.RouteInformation
import spock.lang.Specification

import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.LocalDateTime

import static java.lang.StrictMath.abs

class RouteServiceSpec extends Specification {
    private static final int EXPECTED_NUMBER_OF_LOGON_LOGOFF_RETURN_STOPS = 3

    RouteItemRepository routeItemRepository = Mock(RouteItemRepository)
    RouteService routeService = new RouteService(routeItemRepository)

    def "The service builds click and collect routes correctly"(){

        given: "A store ID, start date time, and end date time"
        String storeId = "2320"
        LocalDateTime startDateTime = LocalDateTime.of(2020 ,1, 1, 1, 1, 1)
        LocalDateTime endDateTime = LocalDateTime.of(2020 ,1, 1, 2, 1, 1)

        and: "a list of route items"
        def routeItem = new RouteItem()
        routeItem.setStoreId(storeId)
        routeItem.setStoreName("Herne Bay")
        routeItem.setStartDateTime(startDateTime)
        routeItem.setEndDateTime(endDateTime)
        List<RouteItem> routeItems = List.of(routeItem)

        when: "the service is called"
        List<RouteInformation> output = routeService.buildClickAndCollectRoutes(storeId, startDateTime, endDateTime)

        then: "the repository is called"
        1 * routeItemRepository.findByStoreIdAndLockedAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(storeId, true, startDateTime, endDateTime) >> routeItems

        and: "the output is contains the correct data"
        output[0].storeId == storeId
        output[0].storeName == "Herne Bay"
        output[0].originalShiftId == "2320C_Van 81-20200101-0"
        output[0].vanNumber == "81"
        output[0].clickAndCollect == true
        output[0].storeDepartureDateTime == LocalDateTime.of(2020, 1, 1, 6, 30, 0)
        output[0].stops.size() == routeItems.size() + EXPECTED_NUMBER_OF_LOGON_LOGOFF_RETURN_STOPS

        def dropStop = output[0].stops.find { stop -> stop.type == "DROP"}
        def logonStop = output[0].stops.find { stop -> stop.type == "LOGON"}
        def logoffStop = output[0].stops.find { stop -> stop.type == "LOGOFF"}
        def returnStop = output[0].stops.find { stop -> stop.type == "RETURN"}

        dropStop != null
        logonStop != null
        logoffStop != null
        returnStop != null

        dropStop.orderNumber == routeItem.orderNumber
        dropStop.slotDateTime == startDateTime
        dropStop.arrivalDateTime == startDateTime
        dropStop.departureDateTime == endDateTime
        dropStop.placeWindow == 60
        dropStop.type == "DROP"

        logonStop.type == "LOGON"
        logonStop.arrivalDateTime == startDateTime

        logoffStop.type == "LOGOFF"
        logoffStop.arrivalDateTime == endDateTime

        returnStop.type == "RETURN"
        returnStop.arrivalDateTime == endDateTime

    }

    def "Empty list returned when no routes retrieved from repository"() {

        given: "A store ID, start date time, and end date time"
        String storeId = "2320"
        LocalDateTime startDateTime = LocalDateTime.of(2020 ,1, 1, 1, 1, 1)
        LocalDateTime endDateTime = LocalDateTime.of(2020 ,2, 2, 2, 2, 2)

        when: "the service is called"
        List<RouteInformation> output = routeService.buildClickAndCollectRoutes(storeId, startDateTime, endDateTime)

        then: "the repository is called"
        1 * routeItemRepository.findByStoreIdAndLockedAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(storeId, true, startDateTime, endDateTime) >> []

        and: "the service returns and empty list"
        output.size() == 0

    }

    def "Can update routes to be locked for specified store"() {
        given: "A store ID and lock date"
        String storeId = "2320"
        LocalDate lockDate = LocalDate.of(2020 ,1, 1)
        LocalDateTime startDateTime = LocalDateTime.of(lockDate, LocalTime.MIDNIGHT)
        LocalDateTime endDateTime = LocalDateTime.of(lockDate, LocalTime.MAX)

        when: "the service is called"
        routeService.lockRoutesFor(storeId, lockDate)

        then: "the repository is called to update the route locked flag"
        1 * routeItemRepository.updateLockedForStoreAndDate(storeId, true, startDateTime, endDateTime) >> 1

    }

}
