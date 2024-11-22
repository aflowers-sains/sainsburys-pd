package com.sainsburys.pd.restdata.data;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Route {
    @Id
    @GeneratedValue
    public Long id;

    @Column
    public String name;

    @Column
    public LocalDateTime dateTime;

    @OneToMany(mappedBy = "route")
    public List<Stop> stops;
}
