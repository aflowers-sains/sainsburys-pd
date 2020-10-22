package com.sainsburys.psr.fcrs.service.flrs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    @JsonProperty("fulfilmentLocation")
    private FulfilmentLocation fulfilmentLocation;
}
