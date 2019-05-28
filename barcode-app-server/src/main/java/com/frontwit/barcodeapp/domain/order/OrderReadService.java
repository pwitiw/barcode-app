package com.frontwit.barcodeapp.domain.order;

import com.frontwit.barcodeapp.domain.order.dto.OrderDetailDto;
import com.frontwit.barcodeapp.domain.order.dto.OrderDto;
import com.frontwit.barcodeapp.domain.order.dto.OrderNotFoundException;
import com.frontwit.barcodeapp.domain.order.ports.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class OrderReadService {

    private OrderRepository orderRepository;

    public OrderReadService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<OrderDto> findAll(Pageable pageable) {
        return orderRepository
                .findAll(pageable)
                .map(Order::dto);
    }

    public OrderDetailDto findOne(Long id) {
        return orderRepository.findOne(id)
                .map(Order::detailDto)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}
