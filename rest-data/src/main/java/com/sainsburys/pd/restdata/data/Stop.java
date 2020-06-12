package com.sainsburys.pd.restdata.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
