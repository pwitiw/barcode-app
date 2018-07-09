package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dao.OrderDao;
import com.frontwit.barcodeapp.dto.OrderDto;
import com.frontwit.barcodeapp.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.mapper.OrderMapper;
import com.frontwit.barcodeapp.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderMapper mapper;
    private OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao, OrderMapper mapper) {
        this.orderDao = orderDao;
        this.mapper = mapper;
    }

    @Override
    public Page<OrderDto> getOrders(Pageable pageable, OrderSearchCriteria searchCriteria) {
        Page<Order> orderPage = orderDao.findAll(pageable, searchCriteria);
        LOG.debug("Orders have been collected.");
        return orderPage.map(mapper::map);
    }
}
