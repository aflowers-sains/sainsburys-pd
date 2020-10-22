package com.sainsburys.psr.fcrs.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.sainsburys.psr.fcrs.data.RouteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RouteItemRepository extends JpaRepository<RouteItem, Long> {

    List<RouteItem> findByStoreIdAndLockedAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(
            String storeId,
            boolean locked,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    );

    int deleteAllByOrderNumberAndFlrsIdAndStartDateTime(String orderNumber, String flrsId, LocalDateTime startDateTime);

    @SuppressWarnings("checkstyle:LineLength")
    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("UPDATE RouteItem r SET r.locked = :locked WHERE r.storeId = :storeId and r.startDateTime >= :startDateTime and r.endDateTime <= :endDateTime")
    int updateLockedForStoreAndDate(String storeId, boolean locked, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<RouteItem> findAllByOrderNumberIn(List<String> orderNumbers);

    @Modifying
    @Query(value = "DELETE FROM RouteItem ri WHERE ri.startDateTime < :date")
    int deleteAllByStartDateTimeBefore(LocalDateTime date);
}
