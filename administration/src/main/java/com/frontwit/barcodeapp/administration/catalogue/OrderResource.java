package com.frontwit.barcodeapp.administration.catalogue;

import com.frontwit.barcodeapp.administration.catalogue.dto.*;
import com.frontwit.barcodeapp.administration.processing.front.application.FrontProcessor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @PutMapping(value = "/orders/status")
    void changeStatus(@RequestBody OrderStatusesDto dto) {
        orderCommand.updateStatuses(dto.getIds(), dto.isCompleted());
    }

    @PutMapping(value = "/orders/{orderId}/deadline")
    void updateOrder(@PathVariable Long orderId, @RequestBody UpdateOrderDto dto) {
        orderCommand.updateOrder(UpdateOrder.of(orderId, dto.getDeadline(), dto.getPrice()));
    }

    @GetMapping("/orders/reminders")
    public Page<ReminderDto> getReminders(@RequestParam Integer page, @RequestParam Integer size) {
        return orderQuery.findDeadlines(PageRequest.of(page, size));
    }

    @GetMapping(value = "/routes")
    List<CustomerOrdersDto> getOrdersForRoute(@RequestParam String routes) {
        return orderQuery.findCustomersWithOrdersForRoute(routes);
    }
}