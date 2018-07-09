package com.frontwit.barcodeapp.dto;

import com.frontwit.barcodeapp.model.Order;

import java.time.LocalDate;

public class OrderDto {

    public String id;
    public String name;
    public LocalDate orderedAt;
    public Integer componentAmount;
    private Integer missingComponents;

    public static OrderDto valueOf(Order order) {
        OrderDto dto = new OrderDto();
        order.getId().getCounter();
        dto.id = order.getId().toHexString();
        dto.name = order.getName();
        dto.orderedAt = order.getOrderedAt();
        dto.componentAmount = order.getComponentAmount();
        dto.missingComponents = order.getDamagedComponentsAmount();
        return dto;
    }

    public static Order toOrder(OrderDto dto) {
        Order order = new Order();
        order.setName(dto.name);
        order.setOrderedAt(dto.orderedAt);
        order.setDamagedComponentsAmount(dto.missingComponents);
        order.setComponentAmount(dto.componentAmount);
        return order;
    }
}
