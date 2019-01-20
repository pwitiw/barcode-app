package com.frontwit.barcodeapp.dao;

import com.frontwit.barcodeapp.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Set;

public interface OrderDao {

    Order findOne(Long id);

    Order save(Order order);

    Collection<Order> findByBarcodes(Set<Long> barcodes);

    Page<Order> findAll(Pageable pageable);

    Iterable<Order> save(Collection<Order> orders);

    Page<Order> findForCriteria(Pageable pageable, OrderSearchCriteria searchCriteria);
}
