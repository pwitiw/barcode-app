package com.frontwit.barcodeapp.rest;

import com.frontwit.barcodeapp.dto.OrderDetailDto;
import com.frontwit.barcodeapp.dto.OrderDto;
import com.frontwit.barcodeapp.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.logic.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    //TODO przypadki uzycia -resty rozpisac
    //TODO anty cors
    //TODO security

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    Page<OrderDto> getOrders(Pageable pageable) {
        return orderService.getOrders(pageable);
    }

    @GetMapping("{id}")
    OrderDetailDto getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @PostMapping("/search")
    Page<OrderDto> getOrdersForSearchCriteria(Pageable pageable, @RequestBody OrderSearchCriteria searchCriteria) {
        return orderService.getOrders(pageable, searchCriteria);
    }
}
