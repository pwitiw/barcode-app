package com.frontwit.barcodeapp.administration.catalogue.routes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryOrderDto {
    private Long id;
    private String name;
    private int quantity;
    private double valuation;
}
