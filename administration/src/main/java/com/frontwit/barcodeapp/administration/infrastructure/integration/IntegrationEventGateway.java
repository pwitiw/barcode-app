package com.frontwit.barcodeapp.administration.infrastructure.integration;

import com.frontwit.barcodeapp.administration.processing.front.model.FrontProcessed;
import com.frontwit.barcodeapp.api.shared.Barcode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

import static com.frontwit.barcodeapp.api.shared.Stage.PACKING;

@RequiredArgsConstructor
@Service
public class IntegrationEventGateway {
    private final IntegrationEventPublisher integrationEventPublisher;
    private final FrontDetailsProvider frontDetailsProvider;

    @Scheduled(initialDelay = 1_000, fixedDelay = Long.MAX_VALUE)
     void test() {
        handle(new FrontProcessed(new Barcode(101L), PACKING, LocalDateTime.now()));
    }

    @EventListener
    public void handle(FrontProcessed event) {
        if (event.getStage() != PACKING) {
            return;
        }
        frontDetailsProvider.find(event.getBarcode())
                .ifPresent(integrationEventPublisher::publish);
    }
}

