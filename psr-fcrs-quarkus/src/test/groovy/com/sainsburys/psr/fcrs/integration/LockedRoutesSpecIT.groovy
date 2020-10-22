package com.sainsburys.psr.fcrs.integration

import com.sainsburys.psr.fcrs.IntegrationTestBase
import com.sainsburys.psr.fcrs.data.RouteItem
import com.sainsburys.psr.fcrs.repository.RouteItemRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Unroll

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class LockedRoutesSpecIT extends IntegrationTestBase {
    private static final int EXPECTED_NUMBER_OF_LOGON_LOGOFF_RETURN_STOPS = 3

    @Autowired
    RouteItemRepository routeItemRepository

    def setup() {
        routeItemRepository.deleteAll()
    }

    @Unroll
    def "Returns requested locked route"() {
        given: "A store ID, and start/end date times to query"
        String storeId = "2320"
        String startDateTime = "2020-01-01T00:01:01"
        String endDateTime = "2020-01-01T00:05:01"

        and: "A locked route exists in the database"
        orderNumbers.each { orderNumber ->
            def routeItem = new RouteItem()
            routeItem.setStoreId(storeId)
            routeItem.setStoreName('Herne Bay')
            routeItem.setFlrsId("Q0xJQ0tfQU5EX0NPTExFQ1Q6ZGJmNjM4ODItODQ1ZS00MmExLWFiM2UtMjVkZWEyMmE1ODFm")
            routeItem.setOrderNumber(orderNumber)
            routeItem.setStartDateTime(LocalDateTime.parse(startDateTime, DateTimeFormatter.ISO_DATE_TIME))
            routeItem.setEndDateTime(LocalDateTime.parse(endDateTime, DateTimeFormatter.ISO_DATE_TIME))
            routeItem.setLocked(true)
            routeItemRepository.save(routeItem)
        }

        when: "we query the service"
        def response = mvc.perform(get("/api/routes/locked")
                .accept(APPLICATION_JSON)
                .queryParam("storeId", storeId)
                .queryParam("startDateTime", startDateTime)
                .queryParam("endDateTime", endDateTime)
        )

        then: "The response is ok"
        response.andExpect(status().isOk())

        and: "the response is indicating JSON"
        response.andExpect(content().contentType(APPLICATION_JSON))

        and: "the body of the response contains the expected values"
        response.andExpect(jsonPath('$[0].storeId').value('2320'))
                .andExpect(jsonPath('$[0].storeName').value('Herne Bay'))
                .andExpect(jsonPath('$[0].originalShiftId').value('2320C_Van 81-20200101-0'))
                .andExpect(jsonPath('$[0].vanNumber').value('81'))
                .andExpect(jsonPath('$[0].clickAndCollect').value(true))
                .andExpect(jsonPath('$[0].storeDepartureDateTime').value('2020-01-01T06:30:00'))
                .andExpect(jsonPath('$[0].stops', hasSize(orderNumbers.size() + EXPECTED_NUMBER_OF_LOGON_LOGOFF_RETURN_STOPS)))
                .andExpect(jsonPath('$[0].stops[?(@.type=="DROP")].orderNumber').value(containsInAnyOrder(*orderNumbers)))
                .andExpect(jsonPath('$[0].stops[?(@.type=="DROP")].slotDateTime').value(containsInAnyOrder(*startTimes)))
                .andExpect(jsonPath('$[0].stops[?(@.type=="DROP")].arrivalDateTime').value(containsInAnyOrder(*startTimes)))
                .andExpect(jsonPath('$[0].stops[?(@.type=="DROP")].departureDateTime').value(containsInAnyOrder(*endTimes)))
                .andExpect(jsonPath('$[0].stops[?(@.type=="DROP")].placeWindow').value(containsInAnyOrder(*placeWindows)))
                .andExpect(jsonPath('$[0].stops[?(@.type=="DROP")].type').value(containsInAnyOrder(*types)))
                .andExpect(jsonPath('$[0].stops[?(@.type=="LOGON")].arrivalDateTime').value(startTimes[0]))
                .andExpect(jsonPath('$[0].stops[?(@.type=="LOGOFF")].arrivalDateTime').value(endTimes[0]))
                .andExpect(jsonPath('$[0].stops[?(@.type=="RETURN")].arrivalDateTime').value(endTimes[0]))

        where:
        orderNumbers          | startTimes                                     | endTimes                                       | placeWindows | types
        ["chicken"]           | ["2020-01-01T00:01:01"]                        | ["2020-01-01T00:05:01"]                        | [4]          | ["DROP"]
        ["chicken", "badger"] | ["2020-01-01T00:01:01", "2020-01-01T00:01:01"] | ["2020-01-01T00:05:01", "2020-01-01T00:05:01"] | [4, 4]       | ["DROP", "DROP"]
    }

    def "Returns empty list of locked routes if none locked"() {
        given: "A store ID, and start/end date times to query"
        String storeId = "2320"
        String startDateTime = "2020-01-01T00:01:01"
        String endDateTime = "2020-01-01T00:05:01"

        and: "A locked route exists in the database"
        orderNumbers.each { orderNumber ->
            def routeItem = new RouteItem()
            routeItem.setStoreId(storeId)
            routeItem.setFlrsId("Q0xJQ0tfQU5EX0NPTExFQ1Q6ZGJmNjM4ODItODQ1ZS00MmExLWFiM2UtMjVkZWEyMmE1ODFm")
            routeItem.setOrderNumber(orderNumber)
            routeItem.setStartDateTime(LocalDateTime.parse(startDateTime, DateTimeFormatter.ISO_DATE_TIME))
            routeItem.setEndDateTime(LocalDateTime.parse(endDateTime, DateTimeFormatter.ISO_DATE_TIME))
            routeItem.setLocked(false)
            routeItemRepository.save(routeItem)
        }

        when: "we query the service"
        def response = mvc.perform(get("/api/routes/locked")
                .accept(APPLICATION_JSON)
                .queryParam("storeId", storeId)
                .queryParam("startDateTime", startDateTime)
                .queryParam("endDateTime", endDateTime)
        )

        then: "The response is ok"
        response.andExpect(status().isOk())

        and: "the response is indicating JSON"
        response.andExpect(content().contentType(APPLICATION_JSON))

        and: "the body of the response contains an empty list"
        response.andExpect(jsonPath('$.*', hasSize(0)))

        where:
        orderNumbers          | _
        ["chicken"]           | _
        ["chicken", "badger"] | _
    }

    def "Lock routes for the specified store and date"() {
        given: "A store ID, and date to lock routes for"
        String storeId = "2320"
        String dateToLockRoutesFor = "2020-01-01"

        and: "Route for store and date exist in the database"
        orderNumbers.each { orderNumber ->
            def routeItem = new RouteItem()
            routeItem.setStoreId(storeId)
            routeItem.setStoreName('Herne Bay')
            routeItem.setFlrsId("Q0xJQ0tfQU5EX0NPTExFQ1Q6ZGJmNjM4ODItODQ1ZS00MmExLWFiM2UtMjVkZWEyMmE1ODFm")
            routeItem.setOrderNumber(orderNumber)
            routeItem.setStartDateTime(LocalDate.parse(dateToLockRoutesFor, DateTimeFormatter.ISO_DATE).atStartOfDay())
            routeItem.setEndDateTime(LocalDate.parse(dateToLockRoutesFor, DateTimeFormatter.ISO_DATE).atTime(hour, 59, 59))
            routeItem.setLocked(false)
            routeItemRepository.save(routeItem)
        }

        when: "we lock the routes"
        def response = mvc.perform(post("/api/routes/lock")
                .accept(APPLICATION_JSON)
                .queryParam("storeId", storeId)
                .queryParam("lockDate", dateToLockRoutesFor)
        )

        then: "The response indicates we did whatever locking we could"
        response.andExpect(status().isOk())

        and: "the database should have set the lock flag"
        def routeItems = routeItemRepository.findAll()
        routeItems.size() == orderNumbers.size()

        if (routeItems.size() > 0) {
            routeItems.each { item ->
                assert item.locked == true
            }
        }

        where:
        orderNumbers          | hour
        []                    | 23
        ["chicken"]           | 23
        ["chicken", "badger"] | 13

    }


    def "Lock routes for the specified store and date and no other dates"() {
        given: "A store ID, and date to lock routes for"
        String storeId = "2320"
        String dateToLockRoutesFor = "2020-01-01"

        and: "Route for store and this date exists in the database"
        def routeItem = new RouteItem()
        routeItem.setStoreId(storeId)
        routeItem.setStoreName('Herne Bay')
        routeItem.setFlrsId("Q0xJQ0tfQU5EX0NPTExFQ1Q6ZGJmNjM4ODItODQ1ZS00MmExLWFiM2UtMjVkZWEyMmE1ODFm")
        routeItem.setOrderNumber("today")
        routeItem.setStartDateTime(LocalDate.parse(dateToLockRoutesFor, DateTimeFormatter.ISO_DATE).atStartOfDay())
        routeItem.setEndDateTime(LocalDate.parse(dateToLockRoutesFor, DateTimeFormatter.ISO_DATE).atTime(13, 59, 59))
        routeItem.setLocked(false)
        routeItemRepository.save(routeItem)

        and: "Route for store and next day exists in the database"
        routeItem = new RouteItem()
        routeItem.setStoreId(storeId)
        routeItem.setStoreName('Herne Bay')
        routeItem.setFlrsId("Q0xJQ0tfQU5EX0NPTExFQ1Q6ZGJmNjM4ODItODQ1ZS00MmExLWFiM2UtMjVkZWEyMmE1ODFm")
        routeItem.setOrderNumber("tomorrow")
        routeItem.setStartDateTime(LocalDate.parse(dateToLockRoutesFor, DateTimeFormatter.ISO_DATE).plusDays(1).atStartOfDay())
        routeItem.setEndDateTime(LocalDate.parse(dateToLockRoutesFor, DateTimeFormatter.ISO_DATE).plusDays(1).atTime(14, 59, 59))
        routeItem.setLocked(false)
        routeItemRepository.save(routeItem)

        when: "we lock the routes"
        def response = mvc.perform(post("/api/routes/lock")
                .accept(APPLICATION_JSON)
                .queryParam("storeId", storeId)
                .queryParam("lockDate", dateToLockRoutesFor)
        )

        then: "The response indicates we did whatever locking we could"
        response.andExpect(status().isOk())

        and: "the database should have set the lock flag for today and not tomorrow"
        def routeItems = routeItemRepository.findAll()
        routeItems.size() == 2

        routeItems.each { item ->
            assert item.locked == (item.orderNumber == "today")
        }
    }
}
