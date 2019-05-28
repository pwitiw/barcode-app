package com.frontwit.barcodeapp.rest;

import com.frontwit.barcodeapp.domain.order.OrderFacade;
import com.frontwit.barcodeapp.domain.order.dto.OrderDetailDto;
import com.frontwit.barcodeapp.domain.order.dto.ProcessCommand;
import com.frontwit.barcodeapp.domain.synchronization.SynchronizationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/process")
public class OrderProcessingResource {

    @Autowired
    OrderFacade orderFacade;

    @Autowired
    SynchronizationFacade synchronizationFacade;

    @PostMapping
    void process(List<ProcessCommand> processCommands) {
        orderFacade.process(processCommands);
    }

    @GetMapping
    OrderDetailDto test() {
        return synchronizationFacade.getOrder(1);
    }

}
