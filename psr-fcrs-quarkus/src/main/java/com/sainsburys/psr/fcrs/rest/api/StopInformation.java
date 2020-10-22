package com.sainsburys.psr.fcrs.rest.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StopInformation {
    private String stopId;
    private String type;
    private Integer number;
    private Integer itemCount;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime arrivalDateTime;
    private LocalDateTime departureDateTime;
    private Integer timeFrom;
    private Integer distanceFrom;
    private LocalDateTime slotDateTime;
    private String postCode;
    private String orderNumber;
    private Integer placeWindow;
    private String customerNumber;
}
