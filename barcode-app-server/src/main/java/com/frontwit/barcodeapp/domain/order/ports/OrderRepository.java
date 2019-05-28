package com.frontwit.barcodeapp.domain.order.ports;

import com.frontwit.barcodeapp.domain.order.Order;
import com.frontwit.barcodeapp.domain.order.dto.OrderSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface OrderRepository {

    Optional<Order> findOne(Long id);

    Order save(Order order);

    Collection<Order> findByIds(Set<Long> barcodes);

    Page<Order> findAll(Pageable pageable);

    Iterable<Order> save(Collection<Order> orders);

    Page<Order> findForCriteria(Pageable pageable, OrderSearchCriteria searchCriteria);
}
