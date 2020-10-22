package com.sainsburys.psr.fcrs.service.flrs;

import com.sainsburys.psr.fcrs.service.flrs.dto.GraphQLRequest;
import com.sainsburys.psr.fcrs.service.flrs.dto.GraphQLResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "flrs", url = "${flrs.url}")
public interface FlrsClient {
    @PostMapping(value = "/graphql")
    GraphQLResponse post(@RequestBody GraphQLRequest request);
}
