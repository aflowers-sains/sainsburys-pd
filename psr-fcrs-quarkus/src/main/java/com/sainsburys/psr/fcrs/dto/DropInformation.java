package com.sainsburys.psr.fcrs.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DropInformation {
    private final LocalDateTime storeDepartureDateTime;
    private final String vanNumber;
    private final String lane;
    private final Integer dropNumber;
    private final LocalDateTime arrivalDateTime;
}
