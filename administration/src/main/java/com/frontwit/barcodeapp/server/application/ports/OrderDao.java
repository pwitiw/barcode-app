package com.frontwit.barcodeapp.server.application.ports;

import com.frontwit.barcodeapp.server.application.order.Order;
import com.frontwit.barcodeapp.server.application.order.dto.OrderSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderDao {

    Optional<Order> findOne(Long id);

    Order save(Order order);

    Page<Order> find(Pageable pageable, OrderSearchCriteria searchCriteria);
}
