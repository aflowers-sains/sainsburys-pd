package com.sainsburys.psr.fcrs.service;

import java.time.LocalDateTime;

import com.sainsburys.psr.fcrs.repository.RouteItemRepository;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DataPurgeService {
    private static final int NUMBER_OF_DAYS = 7;

    private final RouteItemRepository routeItemRepository;

    public DataPurgeService(RouteItemRepository routeItemRepository) {
        this.routeItemRepository = routeItemRepository;
    }

    @Scheduled(cron = "${fcrs.scheduling.cron}")
    @SchedulerLock(name = "TaskScheduler_scheduledTask")
    @Transactional
    public void purgeOldData() {
        int numberDeleted = routeItemRepository.deleteAllByStartDateTimeBefore(LocalDateTime.now().minusDays(NUMBER_OF_DAYS));

        if (log.isInfoEnabled()) {
            log.info("Scheduled Purge :: Deleted " + numberDeleted + " old routes");
        }
    }

}
