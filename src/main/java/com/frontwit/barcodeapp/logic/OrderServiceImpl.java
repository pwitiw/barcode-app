package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dao.OrderDao;
import com.frontwit.barcodeapp.dto.OrderDetailDto;
import com.frontwit.barcodeapp.dto.OrderDto;
import com.frontwit.barcodeapp.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderDao orderDao;
    private BarcodeService barcodeService;

    public OrderServiceImpl(OrderDao orderDao, BarcodeService barcodeService) {
        this.orderDao = orderDao;
        this.barcodeService = barcodeService;
    }

    @Override
    public Page<OrderDto> getOrders(Pageable pageable) {
        Page<Order> orderPage = orderDao.findAll(pageable);
        LOG.debug("Orders have been collected.");
        return orderPage.map(OrderDto::valueOf);
    }

    @Override
    public Page<OrderDto> getOrders(Pageable pageable, OrderSearchCriteria searchCriteria) {
        Page<Order> orderPage = orderDao.findForCriteria(pageable, searchCriteria);
        LOG.debug("Orders have been collected.");
        return orderPage.map(OrderDto::valueOf);
    }

    @Override
    public OrderDetailDto getOrder(String id) {
        Order order = orderDao.findOne(id);
        LOG.debug("Order have been collected.");
        return OrderDetailDto.valueOf(order);
    }

    @Override
    public void save(OrderDetailDto orderDetail) {
        Order order = new Order();
        order.setBarcode(barcodeService.generate());
        orderDao.save(order);
    }

}
