package com.frontwit.barcodeapp.administration.catalogue;

import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.String.format;

@Component
@AllArgsConstructor
class OrderCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCommand.class);

    private MongoTemplate mongoTemplate;

    void changeStatus(Long id) {
        Optional.ofNullable(mongoTemplate.findById(id, OrderEntity.class))
                .ifPresentOrElse(order -> {
                    order.setCompleted(!order.isCompleted());
                    mongoTemplate.save(order);
                    LOGGER.info(format("STATUS CHANGED {orderId=%s, status=%s}", id, order.isCompleted() ? "COMPLETED" : "NOT COMPLETED"));
                }, () ->  new IllegalArgumentException(format("Order with id %s does not exist", id)));
    }

    void updateDeadline(DeadlineUpdated deadline) {
        Optional.ofNullable(mongoTemplate.findById(deadline.getOrderId(), OrderEntity.class))
                .ifPresentOrElse(order -> {
                    order.setDeadline(deadline.getDeadline());
                    mongoTemplate.save(order);
                    LOGGER.info(deadline.toString());
                }, () -> new IllegalArgumentException(format("Order with id %s does not exist", deadline.getOrderId())));
    }
}