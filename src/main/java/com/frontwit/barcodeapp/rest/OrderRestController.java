package com.frontwit.barcodeapp.rest;

import com.frontwit.barcodeapp.dto.OrderDto;
import com.frontwit.barcodeapp.logic.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/orders")
public class OrderRestController {

    //TODO przypadki uzycia -resty rozpisac
    //TODO Paginacja - ogarnac jak to robic z szukaniem po roznych kryteriach
    //TODO dto
    //TODO anty cors
    //TODO security
    // TODO loggery

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    Page<OrderDto> getOrders(Pageable pageable) {
        return orderService.getOrders(pageable);
    }
}
