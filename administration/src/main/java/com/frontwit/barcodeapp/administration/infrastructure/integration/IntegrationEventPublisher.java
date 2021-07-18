package com.frontwit.barcodeapp.administration.infrastructure.integration;

import com.frontwit.barcodeapp.api.integration.PrintableLabelEvent;

public interface IntegrationEventPublisher {
    void publish(PrintableLabelEvent event);

}
