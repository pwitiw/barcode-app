package com.frontwit.barcodeapp.administration.processing.front.model;

import lombok.NonNull;
import lombok.Value;

@Value
class StageUpdated implements DomainEvent {
    @NonNull Barcode barcode;
    @NonNull Stage stage;
}
