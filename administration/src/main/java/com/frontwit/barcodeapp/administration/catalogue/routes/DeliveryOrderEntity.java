package com.frontwit.barcodeapp.administration.catalogue.routes;

import com.frontwit.barcodeapp.administration.catalogue.routes.dto.DeliveryOrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class DeliveryOrderEntity {
    private Long id;
    private String name;
    private int quantity;
    private double valuation;

    static DeliveryOrderEntity of(DeliveryOrderDto dto) {
        return new DeliveryOrderEntity(dto.getId(), dto.getName(), dto.getQuantity(), dto.getValuation());
    }

    public DeliveryOrderDto dto() {
        return DeliveryOrderDto.builder()
                .id(id)
                .name(name)
                .quantity(quantity)
                .valuation(valuation)
                .build();
    }
}
