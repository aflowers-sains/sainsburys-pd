package com.sainsburys.psr.fcrs.service.flrs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphQLResponse {
    private Data data;
}
