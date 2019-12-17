package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.processing.front.application.dto.ProcessFrontCommand;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent;
import lombok.Value;

@Value
public class FrontSynchronized implements DomainEvent {

    ProcessFrontCommand processFrontCommand;

    @Override
    public Long getId() {
        return processFrontCommand.getBarcode();
    }
}
