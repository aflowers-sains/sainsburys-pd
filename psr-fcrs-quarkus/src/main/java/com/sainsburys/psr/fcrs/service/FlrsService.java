package com.sainsburys.psr.fcrs.service;

import java.util.Map;
import java.util.Optional;

import com.sainsburys.psr.fcrs.service.flrs.FlrsClient;
import com.sainsburys.psr.fcrs.service.flrs.dto.*;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@Slf4j
public class FlrsService {
    private static final String FLRS_FULFILMENT_LOCATION_QUERY = "query($id: ID) { fulfilmentLocation(filter:{id: $id}) "
            + "{ ... on ClickAndCollect "
            + "{ attributes "
            + "{ fulfilledBy "
            + "{ ... on SainsburysStore { code name }}}}}}";

    private final FlrsClient flrsClient;

    public FlrsService(FlrsClient flrsClient) {
        this.flrsClient = flrsClient;
    }

    @Cacheable(cacheNames = "fulfilledBy")
    public Optional<FulfilledBy> getFullfilledByForFlrsId(String flrsId) {
        var request = new GraphQLRequest();
        request.setQuery(FLRS_FULFILMENT_LOCATION_QUERY);
        request.setVariables(Map.of("id", flrsId));

        try {
            return transform(flrsClient.post(request));
        } catch (FeignException e) {
            log.error("An unexpected error occurred when fetching fulfilment location from FLRS", keyValue("message", e.getMessage()));

            throw e;
        }
    }

    private Optional<FulfilledBy> transform(GraphQLResponse graphQLResponse) {
        return Optional.ofNullable(graphQLResponse.getData())
                .map(Data::getFulfilmentLocation)
                .map(FulfilmentLocation::getAttributes)
                .map(ClickAndCollectAttributes::getFulfilledBy);
    }
}
