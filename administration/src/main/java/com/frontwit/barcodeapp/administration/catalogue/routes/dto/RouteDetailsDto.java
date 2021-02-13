package com.frontwit.barcodeapp.administration.catalogue.routes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteDetailsDto {
    private String id;
    private String name;
    private Instant date;
    private boolean fulfilled;
    private List<DeliveryInfoDto> deliveryInfos;
}
