package com.frontwit.barcodeapp.administration.catalogue.routes;

import com.frontwit.barcodeapp.administration.catalogue.routes.dto.RouteDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Data
@Document(collection = "route")
@NoArgsConstructor
@AllArgsConstructor
class RouteEntity {
    private String id;
    private String name;
    private Instant date;
    private boolean fulfilled;
    private List<DeliveryInfoEntity> deliveryInfos;

    static RouteEntity of(RouteDetailsDto dto) {
        var id = dto.getId() != null ? dto.getId() : UUID.randomUUID().toString();
        var deliveryInfos = dto.getDeliveryInfos().stream()
                .map(DeliveryInfoEntity::of)
                .collect(toList());
        return new RouteEntity(id, dto.getName(), dto.getDate(), dto.isFulfilled(), deliveryInfos);
    }

    public RouteDetailsDto dto() {
        return RouteDetailsDto.builder()
                .id(id)
                .name(name)
                .date(date)
                .fulfilled(fulfilled)
                .deliveryInfos(
                        deliveryInfos.stream()
                                .map(DeliveryInfoEntity::dto)
                                .collect(toList())
                )
                .build();
    }
}
