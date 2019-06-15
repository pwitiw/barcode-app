package com.frontwit.barcodeapp.infrastructure.rest;

import com.frontwit.barcodeapp.application.order.OrderFacade;
import com.frontwit.barcodeapp.application.order.dto.OrderDetailDto;
import com.frontwit.barcodeapp.application.order.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderResource {

    private OrderFacade orderFacade;

    public OrderResource(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @GetMapping
    Page<OrderDto> getOrders(Pageable pageable) {
        return orderFacade.findAll(pageable);
    }

    @GetMapping("/{id}")
    OrderDetailDto getOrder(@PathVariable Long id) {
        return orderFacade.findOne(id);
    }
//
//    @PostMapping("/search")
//    Page<OrderDto> getOrdersForSearchCriteria(Pageable pageable, @RequestBody OrderSearchCriteria searchCriteria) {
//        return orderService.getOrders(pageable, searchCriteria);
//    }
}
