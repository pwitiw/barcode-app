package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import lombok.NonNull;
import lombok.Value;

@Value
public class StageChanged implements DomainEvent {
    @NonNull Barcode barcode;
    @NonNull Stage stage;

    @Override
    public Long getId() {
        return barcode.getBarcode();
    }

    @Override
    public String toString() {
        return "StageChanged{" +
                "barcode=" + barcode +
                ", stage=" + stage +
                '}';
    }
}
