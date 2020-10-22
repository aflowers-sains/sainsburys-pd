package com.sainsburys.psr.fcrs.service.flrs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FulfilledBy {
    private final String code;
    private final String name;

    public FulfilledBy(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
