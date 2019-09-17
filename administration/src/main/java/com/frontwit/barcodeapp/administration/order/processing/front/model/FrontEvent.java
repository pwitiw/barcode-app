package com.frontwit.barcodeapp.administration.order.processing.front.model;

import com.frontwit.barcodeapp.administration.order.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.order.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.order.processing.shared.events.DomainEvent;
import lombok.NonNull;
import lombok.Value;

public interface FrontEvent extends DomainEvent {
    Barcode getBarcode();

    @Value
    class StageChanged implements FrontEvent {
        @NonNull Barcode barcode;
        @NonNull Stage stage;
    }
}