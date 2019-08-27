package com.frontwit.barcodeapp.administration.application.order;

import com.frontwit.barcodeapp.administration.application.order.dto.OrderDetailDto;
import com.frontwit.barcodeapp.administration.application.order.dto.OrderDto;
import com.frontwit.barcodeapp.administration.application.order.dto.OrderSearchCriteria;
import com.frontwit.barcodeapp.administration.application.order.dto.ProcessCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProcessOrderService {

    private OrderProcessingService orderProcessingService;

    private OrderReadService orderReadService;

    public ProcessOrderService(OrderProcessingService orderProcessingService, OrderReadService orderReadService) {
        this.orderProcessingService = orderProcessingService;
        this.orderReadService = orderReadService;
    }

    public Page<OrderDto> gerOrders(Pageable pageable, OrderSearchCriteria criteria) {
        return orderReadService.getOrders(pageable, criteria);
    }

    public OrderDetailDto findOne(Long id) {
        return orderReadService.findOne(id);
    }

    public void process(List<ProcessCommand> commands) {
        orderProcessingService.update(commands);
    }
}
