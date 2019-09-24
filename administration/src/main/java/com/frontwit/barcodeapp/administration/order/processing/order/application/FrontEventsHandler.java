package com.frontwit.barcodeapp.administration.order.processing.order.application;

import com.frontwit.barcodeapp.administration.order.processing.front.model.StageChanged;
import com.frontwit.barcodeapp.administration.order.processing.order.model.OrderRepository;
import com.frontwit.barcodeapp.administration.order.processing.order.model.UpdateStageDetails;
import com.frontwit.barcodeapp.administration.order.processing.shared.ProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;

import static java.lang.String.format;

@AllArgsConstructor
public class FrontEventsHandler {

    private OrderRepository orderRepository;

    @EventListener
    public void handle(StageChanged event) {
        var orderId = event.getBarcode().getOrderId();
        var order = orderRepository.findBy(orderId)
                .orElseThrow(() -> new ProcessingException(format("Order with id %s does not exist.", orderId.getOrderId())));
        order.updateFrontStage(new UpdateStageDetails(event.getBarcode(), event.getStage()));
        orderRepository.save(order);
    }

}
