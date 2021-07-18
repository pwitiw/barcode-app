package com.frontwit.barcodeapp.administration.processing.order.model;

import com.frontwit.barcodeapp.api.shared.Barcode;
import lombok.Value;

@Value
public class PackFront {
    private final Barcode barcode;
}
