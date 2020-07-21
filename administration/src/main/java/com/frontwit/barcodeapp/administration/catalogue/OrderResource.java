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

    private final OrderQuery orderQuery;
    private final OrderCommand orderCommand;
    private final FrontProcessor frontProcessor;

    @GetMapping("/orders/{id}")
    OrderDetailDto getOrder(@PathVariable Long id) {
        return orderQuery.getOrderDetails(id);
    }

    @PostMapping("/orders")
    Page<OrderDto> getOrdersForSearchCriteria(Pageable pageable, @RequestBody(required = false) OrderSearchCriteria searchCriteria) {
        return orderQuery.getOrders(pageable, searchCriteria);
    }

    @PutMapping("/orders/status")
    void changeStatus(@RequestBody OrderStatusesDto dto) {
        orderCommand.updateStatuses(dto.getIds(), dto.isCompleted());
    }

    @PutMapping("/orders/{orderId}/deadline")
    void updateOrder(@PathVariable Long orderId, @RequestBody UpdateOrderDto dto) {
        orderCommand.updateOrder(UpdateOrder.of(orderId, dto.getDeadline(), dto.getValuation()));
    }

    @GetMapping("/orders/reminders")
    public Page<ReminderDto> getReminders(@RequestParam Integer page, @RequestParam Integer size) {
        return orderQuery.findDeadlines(PageRequest.of(page, size));
    }

    @GetMapping("/routes")
    List<CustomerOrdersDto> getOrdersForRoute(@RequestParam String routes) {
        return orderQuery.findCustomersWithOrdersForRoute(routes);
    }
}
