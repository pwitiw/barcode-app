package com.frontwit.barcodeapp.administration.catalogue.routes;

import com.frontwit.barcodeapp.administration.catalogue.routes.dto.DeliveryInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
@NoArgsConstructor
class DeliveryInfoEntity {
    private String customer;
    private String address;
    private String phoneNumber;
    private List<DeliveryOrderEntity> orders;
    private String paymentType;

    static DeliveryInfoEntity of(DeliveryInfoDto dto) {
        var orders = dto.getOrders().stream()
                .map(DeliveryOrderEntity::of)
                .collect(toList());
        return new DeliveryInfoEntity(dto.getCustomer(), dto.getAddress(), dto.getPhoneNumber(), orders, dto.getPaymentType());
    }

    public DeliveryInfoDto dto() {
        return DeliveryInfoDto.builder()
                .customer(customer)
                .address(address)
                .paymentType(paymentType)
                .phoneNumber(phoneNumber)
                .orders(
                        orders.stream()
                                .map(DeliveryOrderEntity::dto)
                                .collect(toList())
                )
                .build();
    }
}
