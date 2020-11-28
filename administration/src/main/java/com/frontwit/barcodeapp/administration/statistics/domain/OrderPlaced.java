package com.frontwit.barcodeapp.administration.statistics.domain;

import com.frontwit.barcodeapp.administration.processing.order.model.OrderType;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import com.frontwit.barcodeapp.administration.statistics.domain.order.Meters;
import lombok.Value;

import java.time.Instant;

@Value
public class OrderPlaced implements DomainEvent {
    private final Meters meters;
    private final Instant orderedAt;
    private final OrderType orderType;
}
