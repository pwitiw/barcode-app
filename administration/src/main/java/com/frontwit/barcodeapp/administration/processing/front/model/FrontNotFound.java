package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class FrontNotFound implements DomainEvent {
    @NonNull
    private Barcode barcode;
    @NonNull
    private Integer stage;
    private LocalDateTime dateTime;

    public OrderId getOrderId() {
        return barcode.getOrderId();
    }
}
