package com.sainsburys.pd.restdata.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.sainsburys.pd.restdata.data.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

@RepositoryRestResource
public interface RouteRepository extends JpaRepository<Route, Long> {

    @RestResource
    List<Route> findAllByDateTimeBetween(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime until);
}
