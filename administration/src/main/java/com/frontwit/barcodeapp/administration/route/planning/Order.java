package com.frontwit.barcodeapp.administration.route.planning;

import com.frontwit.barcodeapp.administration.route.planning.dto.DeliveryOrderDto;
import lombok.Value;

import java.math.BigDecimal;

@Value
class Order {
    private final String name;
    private final int quantity;
    private final BigDecimal price;


    private Order(String name, int quantity, BigDecimal price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    String displayOrder() {
        return name + " - " + quantity + " szt., ";
    }

    static Order of(DeliveryOrderDto dto) {
        String name = dto.getName();
        int quantity = dto.getQuantity();
        BigDecimal price = new BigDecimal(dto.getPrice());

        return new Order(name, quantity, price);
    }
}
