package com.frontwit.barcodeapp.administration.processing.shared.events;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.util.stream.Stream;

@AllArgsConstructor
public class DomainEventPublisher implements DomainEvents {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainEventPublisher.class);
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent... events) {
        Stream.of(events)
                .forEach(event -> {
                    applicationEventPublisher.publishEvent(event);
                    LOGGER.info(event.toString());
                });
    }
}
