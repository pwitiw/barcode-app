package com.frontwit.barcodeapp.domain.order.processing.ports;

import com.frontwit.barcodeapp.domain.order.OrderSearchCriteria;
import com.frontwit.barcodeapp.domain.order.processing.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Set;

public interface OrderRepository {

    Order findOne(Long id);

    Order save(Order order);

    Collection<Order> findByBarcodes(Set<Long> barcodes);

    Page<Order> findAll(Pageable pageable);

    Iterable<Order> save(Collection<Order> orders);

    Page<Order> findForCriteria(Pageable pageable, OrderSearchCriteria searchCriteria);
}
