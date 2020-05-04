package com.frontwit.barcodeapp.administration.route.planning.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RouteInfoDto {
    private List<DeliveryInfoDto> deliveryInfos;
    private String route;
}
