package com.sainsburys.psr.fcrs.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.sainsburys.psr.fcrs.data.RouteItem;
import com.sainsburys.psr.fcrs.dto.DropInformation;
import com.sainsburys.psr.fcrs.dto.OrderInformation;
import com.sainsburys.psr.fcrs.repository.RouteItemRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final RouteItemRepository routeItemRepository;

    public OrderService(RouteItemRepository routeItemRepository) {
        this.routeItemRepository = routeItemRepository;
    }


    public List<OrderInformation> orderInformation(List<String> orderNumbers) {
        List<RouteItem> routeItems = routeItemRepository.findAllByOrderNumberIn(orderNumbers);

        AtomicInteger index = new AtomicInteger(1);
        return routeItems.stream().map(o ->
                new OrderInformation(o.getOrderNumber(), new DropInformation(
                        o.getStartDateTime(),
                        "81",
                        "CC",
                        index.getAndIncrement(),
                        o.getStartDateTime()
                ))
        ).collect(Collectors.toList());
    }
}
