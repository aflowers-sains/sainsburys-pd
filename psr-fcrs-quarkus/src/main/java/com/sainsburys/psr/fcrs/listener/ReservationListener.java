package com.sainsburys.psr.fcrs.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sainsburys.psr.fcrs.dto.ReservedOrder;
import com.sainsburys.psr.fcrs.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Slf4j
@Component
public class ReservationListener {
    public static final String RESERVATION = "reservation";
    public static final String RELEASE = "release";
    private final ReservationService reservationService;
    private final ObjectMapper objectMapper;

    public ReservationListener(ReservationService reservationService, ObjectMapper objectMapper) {
        this.reservationService = reservationService;
        this.objectMapper = objectMapper;
    }

    @SuppressWarnings({"checkstyle:IllegalCatch"})
    @JmsListener(destination = "psr.fcss.v1.orders.reserved", concurrency = "10", containerFactory = "jmsListenerContainerFactory")
    public void listen(String reservedOrderString) {
        try {
            ReservedOrder reservedOrder = objectMapper.readValue(reservedOrderString, ReservedOrder.class);

            if (reservedOrder.getType().equals(RESERVATION)) {
                reservationService.addOrderToRoute(reservedOrder);
            } else if (reservedOrder.getType().equals(RELEASE)) {
                reservationService.removeOrderFromRoute(reservedOrder);
            } else {
                log.warn("Unknown type of the reservation message", keyValue("type", reservedOrder.getType()));
            }

        } catch (JsonProcessingException e) {
            log.error("Failed to convert the following message", keyValue("message", reservedOrderString));
            throw new IllegalArgumentException();
        } catch (Exception ex) {
            log.error("Failed to process the received message", keyValue("message", reservedOrderString), ex);
            throw ex;
        }
    }
}
