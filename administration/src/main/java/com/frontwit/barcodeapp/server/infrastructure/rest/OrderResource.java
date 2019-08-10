package com.frontwit.barcodeapp.server.infrastructure.rest;

import com.frontwit.barcodeapp.server.application.order.OrderFacade;
import com.frontwit.barcodeapp.server.application.order.dto.OrderDetailDto;
import com.frontwit.barcodeapp.server.application.order.dto.OrderDto;
import com.frontwit.barcodeapp.server.application.order.dto.OrderSearchCriteria;
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

    @GetMapping("/{id}")
    OrderDetailDto getOrder(@PathVariable Long id) {
        return orderFacade.findOne(id);
    }

//    @PostMapping
    Page<OrderDto> getOrdersForSearchCriteria(Pageable pageable, @RequestBody(required = false) OrderSearchCriteria searchCriteria) {
        return orderFacade.gerOrders(pageable, searchCriteria);
    }
}
