package com.frontwit.barcodeapp.infrastructure.rest;

import com.frontwit.barcodeapp.application.order.OrderFacade;
import com.frontwit.barcodeapp.application.order.dto.ProcessCommand;
import com.frontwit.barcodeapp.application.synchronization.SynchronizationFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/process")
public class OrderProcessingResource {

    OrderFacade orderFacade;

    SynchronizationFacade synchronizationFacade;

    public OrderProcessingResource(OrderFacade orderFacade, SynchronizationFacade synchronizationFacade) {
        this.orderFacade = orderFacade;
        this.synchronizationFacade = synchronizationFacade;
    }

    @PostMapping
    void process(@RequestBody List<ProcessCommand> processCommands) {
        orderFacade.process(processCommands);
    }
}
