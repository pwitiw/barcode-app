package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.Quantity;
import lombok.Value;

@Value
public class TargetFront {

    private Barcode barcode;
    private Quantity quantity;
    private TargetFront.Dimensions dimensions;
    private String comment;

    @Value
    public static class Dimensions {
        private int height;
        private int width;
    }

}
