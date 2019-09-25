package com.frontwit.barcodeapp.administration.order.processing.front.model;

import com.frontwit.barcodeapp.administration.order.processing.front.application.dto.ProcessFrontCommand;
import com.frontwit.barcodeapp.administration.order.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.order.processing.shared.events.DomainEvent;
import lombok.Value;

@Value
public class FrontNotFound implements DomainEvent {
    OrderId orderId;
    ProcessFrontCommand delayedProcessFrontCommand;


    @Override
    public Long getId() {
        return delayedProcessFrontCommand.getBarcode();
    }
}