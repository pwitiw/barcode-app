package com.frontwit.barcodeapp.administration.route.planning;

import com.frontwit.barcodeapp.administration.route.planning.dto.DeliveryOrderDto;
import lombok.Value;

import java.math.BigDecimal;

@Value
class Order {
    private final String name;
    private final int quantity;
    private final BigDecimal valuation;


    private Order(String name, int quantity, BigDecimal valuation) {
        this.name = name;
        this.quantity = quantity;
        this.valuation = valuation;
    }

    String displayOrder() {
        return name + " - " + quantity + " szt., ";
    }

    static Order of(DeliveryOrderDto dto) {
        String name = dto.getName();
        int quantity = dto.getQuantity();
        BigDecimal valuation = new BigDecimal(dto.getValuation());

        return new Order(name, quantity, valuation);
    }
}
