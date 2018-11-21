package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dao.OrderDao;
import com.frontwit.barcodeapp.dao.RouteDao;
import com.frontwit.barcodeapp.dto.OrderDetailDto;
import com.frontwit.barcodeapp.dto.OrderDto;
import com.frontwit.barcodeapp.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.model.Component;
import com.frontwit.barcodeapp.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderDao orderDao;
    private RouteDao routeDao;
    private BarcodeService barcodeService;

    public OrderServiceImpl(OrderDao orderDao, RouteDao routeDao, BarcodeService barcodeService) {
        this.orderDao = orderDao;
        this.routeDao = routeDao;
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
        LOG.debug(format("Orders collected for criteria: %s", searchCriteria));
        return orderPage.map(OrderDto::valueOf);
    }

    @Override
    public OrderDetailDto getOrder(String id) {
        Order order = orderDao.findOne(id);
        LOG.debug(format("Order collected for id %s.", id));
        return OrderDetailDto.valueOf(order);
    }

    @Override
    public void save(List<OrderDetailDto> dtos) {
        List<Order> orders = dtos.stream()
                .map(dto -> dto.toEntity(routeDao))
                .peek(this::setBarcodes)
                .collect(Collectors.toList());
        orderDao.save(orders);
        LOG.debug(format("%d orders saved successfully.", orders.size()));
    }

    private void setBarcodes(Order order) {
        Long barcode = barcodeService.generate();
        order.setBarcode(barcode);
        for (Component component : order.getComponents()) {
            component.setBarcode(++barcode);
        }
    }
}
