package com.sainsburys.pd.restdata.data;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
