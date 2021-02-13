package com.frontwit.barcodeapp.administration.catalogue.routes;

import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class RouteCompleted implements DomainEvent {
    private final String id;
    private final Set<Long> orderIds;

    public RouteCompleted(String id, Set<Long> orderIds) {
        this.id = id;
        this.orderIds = new HashSet<>(orderIds);
    }
}
