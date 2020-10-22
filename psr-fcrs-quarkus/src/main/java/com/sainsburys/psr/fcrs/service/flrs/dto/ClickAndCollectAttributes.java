package com.sainsburys.psr.fcrs.service.flrs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClickAndCollectAttributes {
    private FulfilledBy fulfilledBy;
}
