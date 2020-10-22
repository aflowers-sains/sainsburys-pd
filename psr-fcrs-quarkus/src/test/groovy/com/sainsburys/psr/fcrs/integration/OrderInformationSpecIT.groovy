package com.sainsburys.psr.fcrs.integration

import com.sainsburys.psr.fcrs.IntegrationTestBase
import com.sainsburys.psr.fcrs.data.RouteItem
import com.sainsburys.psr.fcrs.repository.RouteItemRepository
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import static org.hamcrest.Matchers.hasSize
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class OrderInformationSpecIT extends IntegrationTestBase {
    @Autowired
    RouteItemRepository routeItemRepository;

    def "Returns a list of order information"() {
        given: "some route items exist"
        routesExist()

        when: "requesting the order information"
        def response = mvc.perform(post("/api/orderInformation")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content("""
                    ["chicken"]
                """)
        )

        then: "the response is as expected"
        response.andExpect(status().isOk())
        response.andExpect(content().contentType(APPLICATION_JSON))
        response.andExpect(jsonPath('$', hasSize(1)))
        response.andExpect(jsonPath('$[0].orderNumber').value("chicken"))
        response.andExpect(jsonPath('$[0].dropInformation.storeDepartureDateTime').value("2020-01-01T00:00:00"))
        response.andExpect(jsonPath('$[0].dropInformation.vanNumber').value("81"))
        response.andExpect(jsonPath('$[0].dropInformation.lane').value("CC"))
        response.andExpect(jsonPath('$[0].dropInformation.dropNumber').value("1"))
        response.andExpect(jsonPath('$[0].dropInformation.arrivalDateTime').value("2020-01-01T00:00:00"))
    }

    def routesExist() {
        def routeItem = new RouteItem()
        routeItem.setStoreId("2320")
        routeItem.setStoreName('Herne Bay')
        routeItem.setFlrsId("Q0xJQ0tfQU5EX0NPTExFQ1Q6ZGJmNjM4ODItODQ1ZS00MmExLWFiM2UtMjVkZWEyMmE1ODFm")
        routeItem.setOrderNumber("chicken")
        routeItem.setStartDateTime(LocalDate.parse("2020-01-01", DateTimeFormatter.ISO_DATE).atStartOfDay())
        routeItem.setEndDateTime(LocalDate.parse("2020-01-01", DateTimeFormatter.ISO_DATE).atTime(13, 59, 59))
        routeItem.setLocked(false)
        routeItemRepository.save(routeItem)
    }
}
