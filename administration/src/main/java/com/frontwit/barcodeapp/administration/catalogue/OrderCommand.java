package com.frontwit.barcodeapp.administration.catalogue;

import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

@Component
@AllArgsConstructor
class OrderCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCommand.class);

    private MongoTemplate mongoTemplate;

    public void updateStatuses(Set<Long> ids, boolean completed) {
        Query query = new Query(Criteria.where("id").in(ids));
        Update update = new Update().set("completed", completed);
        UpdateResult result = mongoTemplate.updateMulti(query, update, OrderEntity.class);
        LOGGER.info(format("Status changed {count=%s, status=%s}", result.getModifiedCount(), completed ? "COMPLETED" : "NOT COMPLETED"));
    }

    void updateStatuses(Long id) {
        Optional.ofNullable(mongoTemplate.findById(id, OrderEntity.class))
                .ifPresentOrElse(order -> {
                    order.setCompleted(!order.isCompleted());
                    mongoTemplate.save(order);
                    LOGGER.info(format("STATUS CHANGED {orderId=%s, status=%s}", id, order.isCompleted() ? "COMPLETED" : "NOT COMPLETED"));
                }, () -> new IllegalArgumentException(format("Order with id %s does not exist", id)));
    }

    void updateOrder(UpdateOrder updateOrder) {
        Optional.ofNullable(mongoTemplate.findById(updateOrder.getOrderId(), OrderEntity.class))
                .ifPresentOrElse(order -> {
                    order.setDeadline(updateOrder.getDeadline());
                    order.setValuation(updateOrder.getValuation());
                    mongoTemplate.save(order);
                    LOGGER.info(updateOrder.toString());
                }, () -> new IllegalArgumentException(format("Order with id %s does not exist", updateOrder.getOrderId())));
    }
}