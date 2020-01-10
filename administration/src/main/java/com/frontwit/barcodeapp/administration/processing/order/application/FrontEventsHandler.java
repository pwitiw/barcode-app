package com.frontwit.barcodeapp.administration.processing.order.application;

import com.frontwit.barcodeapp.administration.processing.front.model.StageChanged;
import com.frontwit.barcodeapp.administration.processing.order.model.OrderRepository;
import com.frontwit.barcodeapp.administration.processing.order.model.UpdateStageDetails;
import com.frontwit.barcodeapp.administration.processing.shared.ProcessingException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import static java.lang.String.format;

@AllArgsConstructor
public class FrontEventsHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrontEventsHandler.class);

    private OrderRepository orderRepository;

    @EventListener
    public void handle(StageChanged event) {
        var orderId = event.getBarcode().getOrderId();
        var order = orderRepository.findBy(orderId)
                .orElseThrow(() -> new ProcessingException(format("Order with id %s does not exist.", orderId.getOrderId())));
        order.update(new UpdateStageDetails(event.getBarcode(), event.getStage()));
        LOGGER.debug("Order %s updated to stage %s",orderId.getOrderId(), event.getBarcode());
        orderRepository.save(order);
    }

}
