package com.sainsburys.psr.fcrs.service

import com.sainsburys.psr.fcrs.repository.RouteItemRepository
import spock.lang.Specification

import java.time.LocalDateTime

class DataPurgeServiceSpec extends Specification {
    RouteItemRepository routeItemRepository = Mock(RouteItemRepository)
    DataPurgeService dataPurgeService = new DataPurgeService(routeItemRepository)

    def "Data more than 7 days old is purged"() {
        when: "The data is purged"
        dataPurgeService.purgeOldData()

        then: "Old data will be purged"
        1 * routeItemRepository.deleteAllByStartDateTimeBefore({
            LocalDateTime date -> assert date.isBefore(LocalDateTime.now().minusDays(7))
        })
    }

}
