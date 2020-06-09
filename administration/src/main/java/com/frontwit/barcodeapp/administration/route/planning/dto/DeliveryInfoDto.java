package com.frontwit.barcodeapp.administration.route.planning.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryInfoDto {
    private String customer;
    private String address;
    private String phoneNumber;
    private List<DeliveryOrderDto> orders;
    private String paymentType;
    @JsonIgnore
    private boolean allChecked;
}
