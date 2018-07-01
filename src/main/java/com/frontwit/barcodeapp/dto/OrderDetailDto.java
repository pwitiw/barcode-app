package com.frontwit.barcodeapp.dto;

import com.frontwit.barcodeapp.model.Order;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderDetailDto {

    public Long orderId;
    public String name;
    public Set<ComponentDto> components = new HashSet<>();

    public static OrderDetailDto valueOf(Order order) {
        OrderDetailDto dto = new OrderDetailDto();
        dto.orderId = order.getId();
        dto.name = order.getName();
        dto.name = order.getName();
        dto.components = order.getComponents()
                .stream()
                .map(ComponentDto::valueOf)
                .collect(Collectors.toSet());
        return dto;
    }


}
