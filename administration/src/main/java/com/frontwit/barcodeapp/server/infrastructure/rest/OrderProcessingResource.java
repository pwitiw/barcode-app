package com.frontwit.barcodeapp.server.infrastructure.rest;

import com.frontwit.barcodeapp.server.application.order.OrderFacade;
import com.frontwit.barcodeapp.server.application.order.dto.ProcessCommand;
import com.frontwit.barcodeapp.server.application.synchronization.SynchronizationFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api")
public class OrderProcessingResource {

    OrderFacade orderFacade;

    SynchronizationFacade synchronizationFacade;

    public OrderProcessingResource(OrderFacade orderFacade, SynchronizationFacade synchronizationFacade) {
        this.orderFacade = orderFacade;
        this.synchronizationFacade = synchronizationFacade;
    }

    @PostMapping("/process")
    void process(@RequestBody List<ProcessCommand> processCommands) {
        orderFacade.process(processCommands);
    }
}
