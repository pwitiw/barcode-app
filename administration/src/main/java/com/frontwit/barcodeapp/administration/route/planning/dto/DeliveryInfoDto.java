package com.frontwit.barcodeapp.administration.route.planning.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DeliveryInfoDto {
    private String customer;
    private List<DeliveryOrderDto> orders;
    private String paymentType;
}
