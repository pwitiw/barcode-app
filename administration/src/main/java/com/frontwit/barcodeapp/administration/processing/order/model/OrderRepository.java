package com.frontwit.barcodeapp.administration.processing.order.model;

import com.frontwit.barcodeapp.administration.processing.shared.OrderId;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findBy(OrderId orderId);

    void save(Order order);
}
