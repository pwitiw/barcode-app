package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import lombok.NonNull;
import lombok.Value;

@Value
public class FrontPacked implements DomainEvent {
    @NonNull Barcode barcode;

    @Override
    public Long getId() {
        return barcode.getBarcode();
    }
}
