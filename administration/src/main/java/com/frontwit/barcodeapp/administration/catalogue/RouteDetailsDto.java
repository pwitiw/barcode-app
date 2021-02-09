package com.frontwit.barcodeapp.administration.catalogue;

import com.frontwit.barcodeapp.administration.route.planning.dto.DeliveryInfoDto;
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
