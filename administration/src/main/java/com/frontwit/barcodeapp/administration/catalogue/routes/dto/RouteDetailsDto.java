package com.frontwit.barcodeapp.administration.catalogue.routes.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class RouteDetailsDto {
    private String id;
    private String name;
    private Instant date;
    private boolean fulfilled;
    private List<DeliveryInfoDto> deliveryInfos;
}
