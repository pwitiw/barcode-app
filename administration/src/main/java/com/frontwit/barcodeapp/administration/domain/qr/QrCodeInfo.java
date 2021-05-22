package com.frontwit.barcodeapp.administration.domain.qr;

import com.frontwit.barcodeapp.administration.processing.shared.Dimensions;
import lombok.Value;

@Value
public class QrCodeInfo {
    private final QrOrder order;
    private final QrFront front;

    @Value
    public static class QrFront {
        private final Long barcode;
        private final Dimensions dimensions;
        private final int quantity;
    }

    @Value
    public static class QrOrder {
        private final long id;
        private final String name;
        private final int quantity;
        private final String route;
        private final String customer;
    }
}
