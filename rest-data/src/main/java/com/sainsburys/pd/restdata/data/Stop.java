package com.sainsburys.pd.restdata.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Stop {
    @Id
    @GeneratedValue
    public Long id;

    @Column
    public String details;

    @Column
    public Integer vanNumber;

    @ManyToOne
    @JoinColumn(name = "route_id")
    public Route route;
}
