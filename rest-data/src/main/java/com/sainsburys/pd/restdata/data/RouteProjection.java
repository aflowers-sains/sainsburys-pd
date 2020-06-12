package com.sainsburys.pd.restdata.data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "routes", types = Route.class)
public interface RouteProjection {
    Long getId();
    String getName();
    LocalDateTime getDateTime();
    List<Stop> getStops();
}
