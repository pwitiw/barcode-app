package com.frontwit.barcodeapp.administration.catalogue.routes.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryInfoDto {
    private String customer;
    private String address;
    private String phoneNumber;
    private List<DeliveryOrderDto> orders;
    private String paymentType;
}
