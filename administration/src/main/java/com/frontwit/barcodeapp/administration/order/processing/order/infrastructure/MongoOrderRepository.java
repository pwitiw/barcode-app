package com.frontwit.barcodeapp.administration.order.processing.order.infrastructure;

import com.frontwit.barcodeapp.administration.order.processing.order.model.Order;
import com.frontwit.barcodeapp.administration.order.processing.order.model.OrderRepository;
import com.frontwit.barcodeapp.administration.order.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.order.processing.synchronization.SaveSynchronizedOrder;
import com.frontwit.barcodeapp.administration.order.processing.synchronization.TargetOrder;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

@AllArgsConstructor
public class MongoOrderRepository implements OrderRepository, SaveSynchronizedOrder {

    private MongoTemplate mongoTemplate;

    @Override
    public void save(TargetOrder targetOrder) {
        mongoTemplate.save(new OrderEntity(targetOrder));

    }

    @Override
    public Optional<Order> findBy(OrderId orderId) {
        var entity = mongoTemplate.findById(orderId.getOrderId(), OrderEntity.class);
        return Optional.empty();
    }

    @Override
    public void save(Order order) {
        var entity = mongoTemplate.findById(order.getOrderId().getOrderId(), OrderEntity.class);
        mongoTemplate.save(entity);
    }
}
