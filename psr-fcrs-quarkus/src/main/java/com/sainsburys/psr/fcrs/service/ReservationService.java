package com.sainsburys.psr.fcrs.service;

import com.sainsburys.psr.fcrs.data.RouteItem;
import com.sainsburys.psr.fcrs.dto.ReservedOrder;
import com.sainsburys.psr.fcrs.repository.RouteItemRepository;
import com.sainsburys.psr.fcrs.service.flrs.dto.FulfilledBy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Slf4j
@Service
public class ReservationService {

    private final RouteItemRepository routeItemRepository;
    private final FlrsService flrsService;

    public ReservationService(RouteItemRepository routeItemRepository, FlrsService flrsService) {
        this.routeItemRepository = routeItemRepository;
        this.flrsService = flrsService;
    }

    @Transactional
    public void addOrderToRoute(ReservedOrder reservedOrder) {
        log.debug("Received reservation for order", keyValue("orderNumber", reservedOrder.getOrderNumber()));

        try {
            RouteItem routeItem = new RouteItem();

            FulfilledBy fulfilledBy = flrsService.getFullfilledByForFlrsId(reservedOrder.getFlrsId()).orElse(new FulfilledBy("", ""));
            routeItem.setStoreId(fulfilledBy.getCode());
            routeItem.setStoreName(fulfilledBy.getName());
            routeItem.setFlrsId(reservedOrder.getFlrsId());
            routeItem.setStartDateTime(reservedOrder.getStartDateTime());
            routeItem.setEndDateTime(reservedOrder.getEndDateTime());
            routeItem.setOrderNumber(reservedOrder.getOrderNumber());
            routeItem.setLocked(false);

            routeItemRepository.save(routeItem);
        } catch (DataIntegrityViolationException ex) {
            log.info("Duplicate order number detected in a route, will be ignored",
                    keyValue("orderNumber", reservedOrder.getOrderNumber()));
        }
    }

    @Transactional
    public void removeOrderFromRoute(ReservedOrder reservedOrder) {

        int deletedEntities = routeItemRepository.deleteAllByOrderNumberAndFlrsIdAndStartDateTime(
                reservedOrder.getOrderNumber(),
                reservedOrder.getFlrsId(),
                reservedOrder.getStartDateTime());

        if (deletedEntities == 0) {
            log.warn("Reservation to release not found", keyValue("orderNumber", reservedOrder.getOrderNumber()));
            throw new IllegalArgumentException();
        }
    }
}
