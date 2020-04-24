package com.frontwit.barcodeapp.administration.route.planning;

import com.frontwit.barcodeapp.administration.route.planning.dto.DeliveryOrderDto;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Order {
    private final String name;
    private final int quantity;
    private final BigDecimal pricing;


    private Order(String name, int quantity, BigDecimal pricing) {
        this.name = name;
        this.quantity = quantity;
        this.pricing = pricing;
    }

    String readOrder() {
        return name + " - " + quantity + " szt., ";
    }

    static Order of(DeliveryOrderDto dto) {
        String name = dto.getName();
        int quantity = dto.getQuantity();
        BigDecimal price = new BigDecimal(dto.getPrice());

        return new Order(name, quantity, price);
    }
}
