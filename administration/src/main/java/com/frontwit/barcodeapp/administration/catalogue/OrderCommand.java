package com.frontwit.barcodeapp.administration.catalogue;

import com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.String.format;

@Component
@AllArgsConstructor
class OrderCommand {

    private MongoTemplate mongoTemplate;

    void changeStatus(Long id) {
        Optional.ofNullable(mongoTemplate.findById(id, OrderEntity.class))
                .ifPresentOrElse(order -> {
                    order.setCompleted(!order.isCompleted());
                    mongoTemplate.save(order);
                }, () -> new IllegalArgumentException(format("Order with id %s does not exist", id)));
    }
}
