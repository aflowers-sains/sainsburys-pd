package com.sainsburys.psr.fcrs.rest.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteInformation {
    private String storeId;
    private String storeName;
    private String shiftId;
    private String originalShiftId;
    private String vanNumber;
    private LocalDateTime storeDepartureDateTime;
    private Boolean clickAndCollect;

    @Builder.Default
    private List<StopInformation> stops = new ArrayList<>();

    public void addStopInformation(StopInformation stopInformation) {
        stops.add(stopInformation);
    }
}
