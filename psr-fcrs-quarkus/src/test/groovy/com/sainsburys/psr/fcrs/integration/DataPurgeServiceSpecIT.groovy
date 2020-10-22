package com.sainsburys.psr.fcrs.integration

import ch.qos.logback.classic.Level
import ch.qos.logback.core.read.ListAppender
import com.sainsburys.psr.fcrs.IntegrationTestBase
import com.sainsburys.psr.fcrs.data.RouteItem
import com.sainsburys.psr.fcrs.repository.RouteItemRepository
import com.sainsburys.psr.fcrs.service.DataPurgeService
import org.apache.tomcat.jni.Local
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

class DataPurgeServiceSpecIT extends IntegrationTestBase {

    @Autowired
    DataPurgeService dataPurgeService

    @Autowired
    RouteItemRepository routeItemRepository

    ListAppender logs

    def setup() {
        this.logs = setupLogAppender(DataPurgeService.class)

        routeItemRepository.deleteAll()
    }

    def "Can purge old data"() {
        given: "Some old data is present"
        populateDatabaseWithOldRecords()

        and: "Some current data is present"
        RouteItem newRouteItem = RouteItem.builder()
                .flrsId("xyzzy")
                .storeId("2320")
                .storeName("Herne Bay")
                .orderNumber("neworder")
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now())
                .locked(false)
                .build()
        routeItemRepository.save(newRouteItem)

        when: "We delete data created >7 days ago"
        dataPurgeService.purgeOldData()

        then: "Only one route is left"
        routeItemRepository.findAll().size() == 1

        and: "we logged the number we deleted"
        def log = filterLogItems(logs, Level.INFO)
        log.size() == 1
        log.first().getMessage() == "Scheduled Purge :: Deleted 5 old routes"

        and: "It is the new one"
        routeItemRepository.findAll().get(0).getFlrsId() == "xyzzy"
    }

    private void populateDatabaseWithOldRecords() {
        (1..5).each { idx ->
            RouteItem oldRouteItem = RouteItem.builder()
                    .flrsId("xyzzy")
                    .storeId("2320")
                    .storeName("Herne Bay")
                    .orderNumber("on" + idx)
                    .startDateTime(LocalDateTime.now().minusDays(8))
                    .endDateTime(LocalDateTime.now().minusDays(8))
                    .locked(false)
                    .build()

            routeItemRepository.save(oldRouteItem)
        }
    }
}
