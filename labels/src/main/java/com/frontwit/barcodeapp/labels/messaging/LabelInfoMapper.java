package com.frontwit.barcodeapp.labels.messaging;

import com.frontwit.barcodeapp.api.integration.PrintableLabelEvent;
import com.frontwit.barcodeapp.api.shared.Dimensions;
import com.frontwit.barcodeapp.labels.application.LabelInfo;

public class LabelInfoMapper {

    public static LabelInfo map(PrintableLabelEvent event) {
        return new LabelInfo(order(event), front(event));
    }

    private static LabelInfo.OrderInfo order(PrintableLabelEvent event) {
        return new LabelInfo.OrderInfo(
                event.getOrderId(),
                event.getOrderName(),
                event.getTotalQuantity(),
                event.getRoute(),
                event.getCustomerName()
        );
    }

    private static LabelInfo.FrontInfo front(PrintableLabelEvent event) {
        return new LabelInfo.FrontInfo(
                event.getBarcode(),
                new Dimensions(event.getWidth(), event.getHeight()),
                event.getFrontsQuantity()
        );
    }
}
