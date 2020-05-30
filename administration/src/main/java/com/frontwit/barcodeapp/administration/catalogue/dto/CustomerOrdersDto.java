package com.frontwit.barcodeapp.administration.catalogue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerOrdersDto {
    private String customer;
    private String address;
    private String phoneNumber;
    private List<OrderInfoDto> orders;
    private String paymentType;
}
