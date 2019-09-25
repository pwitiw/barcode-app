package com.frontwit.barcodeapp.administration.order.processing.shared.events;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import static java.lang.String.format;

@AllArgsConstructor
public class DomainEventPublisher implements DomainEvents {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainEventPublisher.class);
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent event) {
        LOGGER.info(format("Event %s published.", event.getClass().getSimpleName()));
        applicationEventPublisher.publishEvent(event);
    }
}
