package com.frontwit.barcodeapp.administration.order.processing.order.infrastructure.web;

import com.frontwit.barcodeapp.administration.order.processing.order.application.dto.OrderDetailDto;
import com.frontwit.barcodeapp.administration.order.processing.order.application.dto.OrderDto;
import com.frontwit.barcodeapp.administration.order.processing.order.application.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.administration.order.processing.order.infrastructure.OrderQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OrderResource {

    OrderQuery orderQuery;

    @GetMapping("/orders/{id}")
    OrderDetailDto getOrder(@PathVariable Long id) {
        return orderQuery.find(id);
    }

    @PostMapping(value = "/orders")
    Page<OrderDto> getOrdersForSearchCriteria(Pageable pageable, @RequestBody(required = false) OrderSearchCriteria searchCriteria) {
        return orderQuery.find(pageable, searchCriteria);
    }
}
