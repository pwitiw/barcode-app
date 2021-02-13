package com.frontwit.barcodeapp.administration.catalogue.routes;

import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class RouteCompleted implements DomainEvent {
    private final String id;
    private final Set<Long> orderIds;
    private final String name;

    public RouteCompleted(String id, String name, Set<Long> orderIds) {
        this.id = id;
        this.name = name;
        this.orderIds = new HashSet<>(orderIds);
    }

    @Override
    public String toString() {
        return "RouteCompleted{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", orderIds=" + orderIds
                + '}';
    }
}
