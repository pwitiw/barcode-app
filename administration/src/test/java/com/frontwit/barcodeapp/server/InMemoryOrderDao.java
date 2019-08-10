package com.frontwit.barcodeapp.server;

import com.frontwit.barcodeapp.server.application.order.Order;
import com.frontwit.barcodeapp.server.application.order.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.server.application.ports.OrderDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryOrderDao implements OrderDao {

    Map<Long, Order> repository = new HashMap<>();

    @Override
    public Optional<Order> findOne(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Order save(Order order) {
        return repository.put(order.getId(), order);
    }

    @Override
    public Page<Order> find(Pageable pageable, OrderSearchCriteria searchCriteria) {
        return null;
    }

//    @Override
//    public Collection<Order> findByIds(Set<Long> ids) {
//        return repository.values().stream()
//                .filter(order -> ids.contains(order.getId()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Page<Order> findAll(Pageable pageable) {
//        int size = repository.size();
//        int pageNr = pageable.getPageNumber();
//        int pageSize = pageable.getPageSize();
//        if (pageNr * pageSize > size) {
//            return Page.empty(pageable);
//        }
//
//        List<Order> collect = repository.values()
//                .stream()
//                .skip(Math.max(pageNr - 1, 0) * pageSize)
//                .limit(pageSize)
//                .collect(Collectors.toList());
//        return new PageImpl(collect);
//    }
//
//    @Override
//    public Iterable<Order> save(Collection<Order> orders) {
//        return null;
//    }
//
//    @Override
//    public Page<Order> findForCriteria(Pageable pageable, OrderSearchCriteria searchCriteria) {
//        return null;
//    }
}
