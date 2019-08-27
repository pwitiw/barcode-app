package com.frontwit.barcodeapp.administration.infrastructure.rest;

import com.frontwit.barcodeapp.administration.application.order.ProcessOrderService;
import com.frontwit.barcodeapp.administration.application.order.dto.OrderDetailDto;
import com.frontwit.barcodeapp.administration.application.order.dto.OrderDto;
import com.frontwit.barcodeapp.administration.application.order.dto.OrderSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderResource {

    private ProcessOrderService orderFacade;

    public OrderResource(ProcessOrderService orderFacade) {
        this.orderFacade = orderFacade;
    }

    @GetMapping("/{id}")
    OrderDetailDto getOrder(@PathVariable Long id) {
        return orderFacade.findOne(id);
    }

    @PostMapping(value = "")
    Page<OrderDto> getOrdersForSearchCriteria(Pageable pageable, @RequestBody(required = false) OrderSearchCriteria searchCriteria) {
        return orderFacade.gerOrders(pageable, searchCriteria);
    }
}
