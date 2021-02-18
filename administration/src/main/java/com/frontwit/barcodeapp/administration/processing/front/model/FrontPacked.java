package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.Quantity;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import lombok.NonNull;
import lombok.Value;

@Value
public class FrontPacked implements DomainEvent {
    @NonNull
    private Barcode barcode;
    private Quantity quantity;

    @Override
    public String toString() {
        return "FrontPacked("
                + "barcode=" + barcode
                + ", quantity=" + quantity.getValue()
                + ')';
    }
}
