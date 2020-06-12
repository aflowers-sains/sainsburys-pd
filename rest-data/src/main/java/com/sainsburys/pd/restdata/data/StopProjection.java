package com.sainsburys.pd.restdata.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "stop", types = Stop.class)
public interface StopProjection {
    Long getId();
    String getDetails();

    @Value("#{T(com.sainsburys.pd.restdata.data.StopProjection).vanNumberToLaneConverter(target.vanNumber)}")
    String getLane();

    Route getRoute();

    static String vanNumberToLaneConverter(Integer vanNumber) {
        return String.valueOf((char)(vanNumber + 'A' - 1));
    }
}
