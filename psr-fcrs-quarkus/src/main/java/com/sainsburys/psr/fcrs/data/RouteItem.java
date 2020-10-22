package com.sainsburys.psr.fcrs.data;

import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "route_item", schema = "fcrs")
public class RouteItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "flrs_id")
    private String flrsId;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Column(name = "locked")
    private Boolean locked;
}
