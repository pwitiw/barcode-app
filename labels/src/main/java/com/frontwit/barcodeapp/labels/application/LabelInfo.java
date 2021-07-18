package com.frontwit.barcodeapp.labels.application;

import com.frontwit.barcodeapp.api.shared.Dimensions;
import lombok.Value;

@Value
public class LabelInfo {
    private final OrderInfo order;
    private final FrontInfo front;

    @Value
    public static class FrontInfo {
        private final Long barcode;
        private final Dimensions dimensions;
        private final int quantity;
    }

    @Value
    public static class OrderInfo {
        private final long id;
        private final String name;
        private final int quantity;
        private final String route;
        private final String customer;
    }
}
