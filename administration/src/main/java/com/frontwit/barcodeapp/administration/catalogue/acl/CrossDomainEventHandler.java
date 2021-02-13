package com.frontwit.barcodeapp.administration.catalogue.acl;

import com.frontwit.barcodeapp.administration.catalogue.orders.OrderCommand;
import com.frontwit.barcodeapp.administration.catalogue.routes.RouteCompleted;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CrossDomainEventHandler {

    private final OrderCommand orderCommand;

    @EventListener
    public void handle(RouteCompleted event) {
        orderCommand.updateStatuses(event.getOrderIds(), true);
    }

}
