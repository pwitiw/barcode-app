package com.frontwit.barcodeapp.administration.catalogue.routes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryOrderDto {
    private Long id;
    private String name;
    private int quantity;
    private double valuation;
    private LocalDate orderedAt;
    private String cutter;
    private String color;
}
