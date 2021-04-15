package com.frontwit.barcodeapp.administration.catalogue.orders.dto;

import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZoneId;

@Data
@AllArgsConstructor
public class OrderInfoDto {
    private static final ZoneId CLIENT_ZONE_ID = ZoneId.of("Europe/Paris");

    private Long id;
    private String name;
    private int quantity;
    private double valuation;
    private LocalDate orderedAt;
    private String cutter;
    private String color;

    public static OrderInfoDto of(OrderEntity order) {
        var valuation = order.getValuation() != null ? order.getValuation().doubleValue() : 0;
        LocalDate zonedOrderedAt = order.getOrderedAt() != null ? LocalDate.ofInstant(order.getOrderedAt(), CLIENT_ZONE_ID) : null;

        return new OrderInfoDto(
                order.getId(),
                order.getName(),
                order.getQuantity(),
                valuation, zonedOrderedAt,
                order.getCutter(),
                order.getColor());
    }
}
