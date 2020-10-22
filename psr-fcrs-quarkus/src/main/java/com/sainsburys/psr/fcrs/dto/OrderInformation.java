package com.sainsburys.psr.fcrs.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class OrderInformation {
    private final String orderNumber;
    private final DropInformation dropInformation;
}
