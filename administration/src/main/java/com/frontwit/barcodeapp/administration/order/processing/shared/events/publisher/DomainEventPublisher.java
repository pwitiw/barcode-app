package com.frontwit.barcodeapp.administration.order.processing.shared.events.publisher;

import com.frontwit.barcodeapp.administration.order.processing.shared.events.DomainEvent;
import com.frontwit.barcodeapp.administration.order.processing.shared.events.DomainEvents;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@AllArgsConstructor
public class DomainEventPublisher implements DomainEvents {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
