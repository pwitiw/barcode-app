package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class FrontSynchronized implements DomainEvent {
    private Barcode barcode;
    private Integer stage;
    private LocalDateTime dateTime;
}
