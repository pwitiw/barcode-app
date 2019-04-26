package com.frontwit.barcodeapp;

import com.frontwit.barcodeapp.domain.order.OrderSearchCriteria;
import com.frontwit.barcodeapp.domain.order.processing.Order;
import com.frontwit.barcodeapp.domain.order.processing.ports.OrderRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryOrderRepository implements OrderRepository {

    Map<ObjectId, Order> repository = new HashMap<>();

    @Override
    public Order findOne(Long barcode) {
        return repository.get(barcode);
    }

    @Override
    public Order save(Order order) {
        return repository.put(order.getId(), order);
    }

    @Override
    // TODO zmienione, moze sie wysrac
    public Collection<Order> findByBarcodes(Set<Long> barcodes) {
        return repository.values().stream()
                .filter(order -> barcodes.contains(order.getId()))
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
