package com.frontwit.barcodeapp.dto;

import com.frontwit.barcodeapp.model.Order;

import java.time.LocalDate;

public class OrderDto {

    public Long id;
    public String name;
    public LocalDate orderedAt;

    public static OrderDto valueOf(Order order) {
        OrderDto dto = new OrderDto();
        dto.id = order.getId();
        dto.name = order.getName();
        dto.orderedAt = order.getOrderedAt();
        return dto;
    }

    public static Order toOrder(OrderDto dto) {
        Order order = new Order();
        order.setName(dto.name);
        order.setOrderedAt(dto.orderedAt);
        return order;
    }
}
