package com.frontwit.barcodeapp.administration.catalogue;

import com.frontwit.barcodeapp.administration.catalogue.dto.OrderDetailDto;
import com.frontwit.barcodeapp.administration.catalogue.dto.OrderDto;
import com.frontwit.barcodeapp.administration.catalogue.dto.OrderSearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OrderResource {

    OrderQuery query;
    OrderCommand orderCommand;

    @GetMapping("/orders/{id}")
    OrderDetailDto getOrder(@PathVariable Long id) {
        return query.find(id);
    }

    @PostMapping(value = "/orders")
    Page<OrderDto> getOrdersForSearchCriteria(Pageable pageable, @RequestBody(required = false) OrderSearchCriteria searchCriteria) {
        return query.find(pageable, searchCriteria);
    }
}
