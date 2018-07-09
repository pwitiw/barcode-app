package com.frontwit.barcodeapp.rest;

import com.frontwit.barcodeapp.dto.OrderDto;
import com.frontwit.barcodeapp.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.logic.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/orders")
public class OrderRestController {

    //TODO przypadki uzycia -resty rozpisac
    //TODO Paginacja - ogarnac jak to robic z szukaniem po roznych kryteriach
    //TODO anty cors
    //TODO security
    // TODO loggery

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    Page<OrderDto> getOrders(Pageable pageable, OrderSearchCriteria searchCriteria) {
        return orderService.getOrders(pageable, searchCriteria);
    }
}
