package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.processing.order.model.OrderType;

public class OrderTypeMapper {

    public static OrderType map(String value) {
        if ("r".equals(value))
            return OrderType.COMPLAINT;
        else if ("z".equals(value)) {
            return OrderType.ORDER;
        }
        return OrderType.ORDER;
    }
}
