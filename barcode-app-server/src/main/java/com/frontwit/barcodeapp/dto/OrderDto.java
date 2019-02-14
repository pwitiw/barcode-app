package com.frontwit.barcodeapp.dto;

import com.frontwit.barcodeapp.model.Component;
import com.frontwit.barcodeapp.model.Order;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class OrderDto {

    private Long barcode;
    private String name;
    private String color;
    private String cutter;
    private LocalDate orderedAt;
    private Integer quantity;
    private long damagedQuantity;

    public static OrderDto valueOf(Order order) {
        OrderDto dto = new OrderDto();
        dto.barcode = order.getBarcode();
        dto.name = order.getName();
        dto.orderedAt = order.getOrderedAt();
        dto.quantity = order.getComponents()
                .stream()
                .mapToInt(Component::getQuantity)
                .sum();
        dto.cutter = order.getCutter();
        dto.color = order.getColor();
        dto.damagedQuantity = order.getComponents().stream().filter(Component::isDamaged).count();
        return dto;
    }
}
