package com.frontwit.barcodeapp.administration.catalogue.dto;

import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerEntity;
import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
public class CustomerOrdersDto {
    private String customer;
    private String address;
    private String phoneNumber;
    private List<OrderInfoDto> orders;
    private String paymentType;

    public static CustomerOrdersDto of(CustomerEntity customer, List<OrderEntity> orders) {
        List<OrderInfoDto> orderInfos = orders.stream()
                .map(OrderInfoDto::of)
                .collect(toList());
        return new CustomerOrdersDto(customer.getName(), customer.getAddress(), customer.getPhoneNumber(), orderInfos, "");
    }
}
