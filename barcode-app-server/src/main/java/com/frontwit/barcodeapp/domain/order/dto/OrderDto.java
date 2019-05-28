package com.frontwit.barcodeapp.domain.order.dto;

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
}
