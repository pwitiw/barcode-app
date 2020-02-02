package com.frontwit.barcodeapp.administration.processing.order.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import lombok.Value;

@Value
public class PackFront {
    private final Barcode barcode;
}
