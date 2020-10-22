package com.sainsburys.psr.fcrs.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.sainsburys.psr.fcrs.rest.api.RouteInformation;
import com.sainsburys.psr.fcrs.service.RouteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping(value = "/routes/locked")
    @SuppressWarnings({"checkstyle:LineLength", "checkstyle:MagicNumber"})
    public ResponseEntity<List<RouteInformation>> findLockedRoutes(@RequestParam("storeId") String storeId,
                                                                   @RequestParam(name = "startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
                                                                   @RequestParam(name = "endDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime
    ) {

        return ResponseEntity.ok(routeService.buildClickAndCollectRoutes(storeId, startDateTime, endDateTime));
    }

    @SuppressWarnings("checkstyle:LineLength")
    @PostMapping(value = "routes/lock")
    public ResponseEntity<Void> lockRoutesForStore(@RequestParam("storeId") String storeId,
                                                   @RequestParam(name = "lockDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lockDate) {

        routeService.lockRoutesFor(storeId, lockDate);
        return ResponseEntity.ok().build();
    }

}
