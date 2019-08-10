package com.frontwit.barcodeapp.administration.application.order;

import com.frontwit.barcodeapp.administration.application.order.dto.OrderDetailDto;
import com.frontwit.barcodeapp.administration.application.order.dto.OrderDto;
import com.frontwit.barcodeapp.administration.application.order.dto.OrderNotFoundException;
import com.frontwit.barcodeapp.administration.application.order.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.administration.application.ports.OrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class OrderReadService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderReadService.class);
    private OrderDao orderDao;

    public OrderReadService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public OrderDetailDto findOne(Long id) {
        return orderDao.findOne(id)
                .map(Order::detailDto)
                .orElseGet(() -> {
                    LOG.info(format("Order with id :%s  was not found", id));
                    throw new OrderNotFoundException();
                });
    }

     Page<OrderDto> getOrders(Pageable pageable, OrderSearchCriteria criteria) {
        return orderDao
                .find(pageable,criteria)
                .map(Order::dto);
    }
}
