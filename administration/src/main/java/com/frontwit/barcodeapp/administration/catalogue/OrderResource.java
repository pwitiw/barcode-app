package com.frontwit.barcodeapp.administration.catalogue;

import com.frontwit.barcodeapp.administration.catalogue.dto.OrderDetailDto;
import com.frontwit.barcodeapp.administration.catalogue.dto.OrderDto;
import com.frontwit.barcodeapp.administration.catalogue.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.administration.processing.front.application.FrontProcessor;
import com.frontwit.barcodeapp.administration.route.planning.dto.DeliveryInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OrderResource {

    OrderQuery orderQuery;
    OrderCommand orderCommand;
    FrontProcessor frontProcessor;

    @GetMapping("/orders/{id}")
    OrderDetailDto getOrder(@PathVariable Long id) {
        return orderQuery.find(id);
    }

    @PostMapping(value = "/orders")
    Page<OrderDto> getOrdersForSearchCriteria(Pageable pageable, @RequestBody(required = false) OrderSearchCriteria searchCriteria) {
        return orderQuery.find(pageable, searchCriteria);
    }

    @PostMapping(value = "/orders/{id}/status")
    void getOrdersForSearchCriteria(@PathVariable Long id) {
        orderCommand.changeStatus(id);
    }

    @GetMapping(value = "")
    List<DeliveryInfoDto> getOrdersForRoutes(@RequestBody List<String> routes){
        return orderQuery.findOrdersForRoutes(routes);
    }
}
