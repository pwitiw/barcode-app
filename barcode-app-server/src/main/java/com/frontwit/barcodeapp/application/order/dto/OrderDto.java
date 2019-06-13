package com.frontwit.barcodeapp.application.order.dto;

import com.frontwit.barcodeapp.application.order.Order;
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

    public static OrderDto from(Order order) {
        return null;
    }
}
