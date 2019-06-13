package com.frontwit.barcodeapp.application.order.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class OrderDetailDto {

    private Long id;
    private String name;
    private String color;
    private String size;
    private String cutter;
    private String comment;
    private String customer;
    private String stage;
    private LocalDate orderedAt;
    private List<ComponentDto> components;

}
