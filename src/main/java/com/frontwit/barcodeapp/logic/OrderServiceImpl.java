package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dto.OrderDto;
import com.frontwit.barcodeapp.mapper.OrderMapper;
import com.frontwit.barcodeapp.model.Order;
import com.frontwit.barcodeapp.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderRepository repository;

    private OrderMapper mapper;

    public OrderServiceImpl(OrderRepository repository,
                            OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<OrderDto> getOrders(Pageable pageable) {
        Page<Order> orderPage = repository.findAll(pageable);
        LOG.debug("Orders downloaded.");
        return orderPage.map(mapper::map);
    }
}
