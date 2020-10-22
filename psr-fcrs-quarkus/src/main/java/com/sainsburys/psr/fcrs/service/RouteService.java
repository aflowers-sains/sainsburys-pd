package com.sainsburys.psr.fcrs.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sainsburys.psr.fcrs.data.RouteItem;
import com.sainsburys.psr.fcrs.repository.RouteItemRepository;
import com.sainsburys.psr.fcrs.rest.api.RouteInformation;
import com.sainsburys.psr.fcrs.rest.api.StopInformation;
import org.springframework.stereotype.Service;

import static java.lang.Math.abs;

@Service
public class RouteService {

    private static final String DROP = "DROP";
    private static final String LOGON = "LOGON";
    private static final String LOGOFF = "LOGOFF";
    private static final String RETURN = "RETURN";
    private static final String VAN_NUMBER = "81";
    private final RouteItemRepository routeItemRepository;

    public RouteService(RouteItemRepository routeItemRepository) {
        this.routeItemRepository = routeItemRepository;
    }

    public List<RouteInformation> buildClickAndCollectRoutes(String storeId, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        List<RouteItem> routeItems = routeItemRepository.
                findByStoreIdAndLockedAndStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(
                        storeId,
                        true,
                        startDateTime,
                        endDateTime
                );

        HashMap<String, RouteInformation> builtRouteInfo = new HashMap<>();

        routeItems.forEach(item -> {
            String routeInformationKey = getRouteInformationKey(item);

            RouteInformation routeInformation = builtRouteInfo.get(routeInformationKey);

            if (routeInformation == null) {
                routeInformation = buildRouteInformation(item);
                builtRouteInfo.put(routeInformationKey, routeInformation);
            }

            routeInformation.addStopInformation(buildStopInformation(item));

        });

        return new ArrayList<>(builtRouteInfo.values());
    }

    private RouteInformation buildRouteInformation(RouteItem item) {
        RouteInformation routeInformation;
        routeInformation = RouteInformation.builder().storeName(item.getStoreName())
                .storeId(item.getStoreId())
                .originalShiftId(buildShiftId(item))
                .vanNumber(VAN_NUMBER)
                .clickAndCollect(true)
                .storeDepartureDateTime(item.getStartDateTime().withHour(6).withMinute(30).withSecond(0))
                .build();

        routeInformation.addStopInformation(StopInformation.builder().arrivalDateTime(item.getStartDateTime()).type(LOGON).build());
        routeInformation.addStopInformation(StopInformation.builder().arrivalDateTime(item.getEndDateTime()).type(LOGOFF).build());
        routeInformation.addStopInformation(StopInformation.builder().arrivalDateTime(item.getEndDateTime()).type(RETURN).build());

        return routeInformation;
    }

    private StopInformation buildStopInformation(RouteItem item) {
        return StopInformation.builder()
                .orderNumber(item.getOrderNumber())
                .slotDateTime(item.getStartDateTime())
                .arrivalDateTime(item.getStartDateTime())
                .departureDateTime(item.getEndDateTime())
                .placeWindow(calculatePlaceWindow(item))
                .type(DROP)
                .build();
    }

    private int calculatePlaceWindow(RouteItem item) {
        return (int) abs(Duration.between(item.getStartDateTime(), item.getEndDateTime()).toMinutes());
    }

    public void lockRoutesFor(String storeId, LocalDate lockDate) {
        LocalDateTime startDateTime = lockDate.atStartOfDay();
        LocalDateTime endDateTime = lockDate.atTime(LocalTime.MAX);

        routeItemRepository.updateLockedForStoreAndDate(storeId, true, startDateTime, endDateTime);
    }

    private String buildShiftId(RouteItem item) {
        return item.getStoreId() + "C_Van 81-" + item.getStartDateTime().format(
                DateTimeFormatter.ofPattern("yyyyMMdd")) + "-0";
    }

    private String getRouteInformationKey(RouteItem item) {
        return item.getStoreId() + item.getStartDateTime() + item.getEndDateTime();
    }

}
