package com.frontwit.barcodeapp.administration.catalogue.orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UpdateOrderDto {
    private long deadline;
    private BigDecimal valuation;
}
