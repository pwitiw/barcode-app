package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dao.OrderDao;
import com.frontwit.barcodeapp.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryOrderDao implements OrderDao {

    Map<Long, Order> repository = new HashMap<>();

    @Override
    public Order findOne(Long id) {
        return repository.get(id);
    }

    @Override
    public Order save(Order order) {
        return repository.put(order.getBarcode(), order);
    }

    @Override
    public Collection<Order> findByBarcodes(Set<Long> barcodes) {
        return repository.values().stream()
                .filter(order -> barcodes.contains(order.getBarcode()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Iterable<Order> save(Collection<Order> orders) {
        return null;
    }

    @Override
    public Page<Order> findForCriteria(Pageable pageable, OrderSearchCriteria searchCriteria) {
        return null;
    }
}
