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
        LOGGER.info(format("Status changed {count=%s, status=%s}", result.getModifiedCount(), parseCompleted(completed)));
    }

    void updateStatuses(Long id) {
        OrderEntity entity = mongoTemplate.findById(id, OrderEntity.class);
        if (entity == null) {
            throw new IllegalArgumentException(orderDoesNotExist(id));
        }
        entity.setCompleted(!entity.isCompleted());
        mongoTemplate.save(entity);
        LOGGER.info(format("STATUS CHANGED {orderId=%s, status=%s}", id, parseCompleted(entity.isCompleted())));
    }

    void updateOrder(UpdateOrder updateOrder) {
        OrderEntity entity = mongoTemplate.findById(updateOrder.getOrderId(), OrderEntity.class);
        if (entity == null) {
            throw new IllegalArgumentException(orderDoesNotExist(updateOrder.getOrderId()));
        }
        entity.setDeadline(updateOrder.getDeadline());
        entity.setValuation(updateOrder.getValuation());
        mongoTemplate.save(entity);
        LOGGER.info(updateOrder.toString());
    }

    private static String parseCompleted(boolean completed) {
        return completed ? "COMPLETED" : "NOT COMPLETED";
    }

    private static String orderDoesNotExist(Long id) {
        return format("Order with id %s does not exist", id);
    }
}
