package com.frontwit.barcodeapp.administration.processing.order.model;

import com.frontwit.barcodeapp.administration.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.processing.synchronization.TargetOrder;

import java.util.Optional;

public interface OrderRepository {
    void save(TargetOrder targetOrder);

    void save(Order order);

    Optional<Order> findBy(OrderId orderId);

    boolean isNotSynchronized(OrderId orderId);
}
