package com.frontwit.barcodeapp.administration.catalogue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderInfoDto {
    private String name;
    private int quantity;
    private double valuation;
}
