package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import lombok.NonNull;
import lombok.Value;

@Value
public class StageChanged implements DomainEvent {
    @NonNull
    private final Barcode barcode;
    @NonNull
    private final Stage stage;

    @Override
    public String toString() {
        return "StageChanged{"
                + "barcode=" + barcode
                + ", stage=" + stage
                + '}';
    }
}
