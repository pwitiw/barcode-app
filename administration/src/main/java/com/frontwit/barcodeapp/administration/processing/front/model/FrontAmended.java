package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.api.shared.Barcode;
import com.frontwit.barcodeapp.api.shared.Stage;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import lombok.Value;

@Value
public class FrontAmended implements DomainEvent {
    private final Barcode barcode;
    private final Stage stage;

    public FrontAmended(Barcode barcode, Stage stage) {
        this.barcode = barcode;

        this.stage = stage;
    }
}
