package com.frontwit.barcodeapp.administration.catalogue.dto;

import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderInfoDto {
    private String name;
    private int quantity;
    private double valuation;

    public static OrderInfoDto of(OrderEntity order) {
        var valuation = order.getValuation() != null ? order.getValuation().doubleValue() : 0;
        return new OrderInfoDto(order.getName(), order.getQuantity(), valuation);
    }
}
