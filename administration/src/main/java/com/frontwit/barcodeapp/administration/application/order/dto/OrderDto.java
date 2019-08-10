package com.frontwit.barcodeapp.administration.application.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class OrderDto {

    private Long id;
    private String name;
    private String color;
    private String cutter;
    private LocalDate orderedAt;
    private Integer quantity;
    private long damagedQuantity;
}
