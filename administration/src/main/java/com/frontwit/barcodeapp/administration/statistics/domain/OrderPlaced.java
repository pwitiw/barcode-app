package com.frontwit.barcodeapp.administration.statistics.domain;

import com.frontwit.barcodeapp.administration.processing.order.model.OrderType;
import com.frontwit.barcodeapp.administration.processing.shared.CustomerId;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import com.frontwit.barcodeapp.administration.processing.synchronization.TargetFront;
import com.frontwit.barcodeapp.administration.statistics.domain.order.Meters;
import lombok.Value;

import java.time.Instant;
import java.util.List;

@Value
public class OrderPlaced implements DomainEvent {
    private final CustomerId customerId;
    private final List<TargetFront> fronts;
    private final Instant orderedAt;
    private final OrderType orderType;
}
