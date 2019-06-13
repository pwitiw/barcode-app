package com.frontwit.barcodeapp.application.ports;

import com.frontwit.barcodeapp.application.order.Order;
import com.frontwit.barcodeapp.application.order.dto.OrderSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface OrderDao {

    Optional<Order> findOne(Long id);

    Order save(Order order);

    Collection<Order> findByIds(Set<Long> barcodes);

    Page<Order> findAll(Pageable pageable);

    Iterable<Order> save(Collection<Order> orders);

    Page<Order> findForCriteria(Pageable pageable, OrderSearchCriteria searchCriteria);
}
