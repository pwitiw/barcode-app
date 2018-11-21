package com.frontwit.barcodeapp.dto;

import com.frontwit.barcodeapp.model.Component;
import com.frontwit.barcodeapp.model.Order;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class OrderDto {

    private String id;
    private String name;
    private LocalDate orderedAt;
    private Integer quantity;
    private long damagedQuantity;

    public static OrderDto valueOf(Order order) {
        OrderDto dto = new OrderDto();
        order.getId().getCounter();
        dto.id = order.getId().toHexString();
        dto.name = order.getName();
        dto.orderedAt = order.getOrderedAt();
        dto.quantity = order.getComponents().size();
        dto.damagedQuantity = order.getComponents().stream().filter(Component::isDamaged).count();
        return dto;
    }
}
