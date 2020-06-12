package com.sainsburys.pd.restdata.repository;

import java.util.List;

import com.sainsburys.pd.restdata.data.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface StopRepository extends JpaRepository<Stop, Long> {

    @RestResource
    public List<Stop> findAllByRouteId(Long id);
}
