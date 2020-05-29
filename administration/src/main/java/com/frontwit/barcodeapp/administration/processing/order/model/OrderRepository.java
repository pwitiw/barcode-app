package com.frontwit.barcodeapp.administration.processing.order.model;

import com.frontwit.barcodeapp.administration.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.processing.synchronization.TargetOrder;

import java.util.Optional;

public interface OrderRepository {
    void save(TargetOrder targetOrder);

    Optional<Order> findBy(OrderId orderId);

    void save(Order order);

    boolean isNotSynchronized(OrderId orderId);
}
