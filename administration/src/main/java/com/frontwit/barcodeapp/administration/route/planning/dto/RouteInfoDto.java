package com.frontwit.barcodeapp.administration.route.planning.dto;

import lombok.Data;

import java.util.List;

@Data
public class RouteInfoDto {
    private List<DeliveryInfoDto> deliveryInfos;
    private String route;
}
