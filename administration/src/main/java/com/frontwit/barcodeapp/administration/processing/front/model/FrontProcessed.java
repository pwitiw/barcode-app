package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class FrontProcessed implements DomainEvent {
    private final Barcode barcode;
    private final Stage stage;
    private final LocalDateTime dateTime;
}
