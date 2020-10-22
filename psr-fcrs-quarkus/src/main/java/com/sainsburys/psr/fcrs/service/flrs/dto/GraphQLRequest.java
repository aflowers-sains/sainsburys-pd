package com.sainsburys.psr.fcrs.service.flrs.dto;

import java.util.Map;

import lombok.Data;

@Data
public class GraphQLRequest {
    private String query;
    private Map<String, Object> variables;
}
