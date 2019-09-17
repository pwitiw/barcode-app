package com.frontwit.barcodeapp.administration.order.processing.order.application;

import com.frontwit.barcodeapp.administration.order.processing.front.model.FrontEvent;
import com.frontwit.barcodeapp.administration.order.processing.order.model.OrderRepository;
import com.frontwit.barcodeapp.administration.order.processing.order.model.UpdateStageDetails;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;

import static java.lang.String.format;

@AllArgsConstructor
public class FrontEventsHandler {

    private OrderRepository orderRepository;

    @EventListener
    public void handle(FrontEvent.StageChanged event) {
        var orderId = event.getBarcode().getOrderId();
        var order = orderRepository.findBy(orderId)
                .orElseThrow(() -> new IllegalStateException(format("Order with id %s does not exist.", orderId.getOrderId())));
        order.updateFrontStage(new UpdateStageDetails(event.getBarcode(), event.getStage()));
        orderRepository.save(order);
    }

}
