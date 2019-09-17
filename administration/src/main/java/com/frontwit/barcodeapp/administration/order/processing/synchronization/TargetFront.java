package com.frontwit.barcodeapp.administration.order.processing.synchronization;


import com.frontwit.barcodeapp.administration.order.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.order.processing.shared.Quantity;
import lombok.Value;

@Value
public class TargetFront {

    private Barcode barcode;
    private Quantity quantity;
    private TargetFront.Dimensions dimensions;
    private String comment;

    @Value
    public static class Dimensions {
        private int width;
        private int height;
    }

}
