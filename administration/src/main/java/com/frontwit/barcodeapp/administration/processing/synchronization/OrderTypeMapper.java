package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.processing.order.model.OrderType;

public final class OrderTypeMapper {
    private static final String COMPLAINT = "r";

    private OrderTypeMapper() {
    }

    public static OrderType map(String value) {
        if (COMPLAINT.equals(value)) {
            return OrderType.COMPLAINT;
        }
        return OrderType.ORDER;
    }
}
