package com.frontwit.barcodeapp.administration.order.processing.order.application.dto;

import com.frontwit.barcodeapp.administration.order.processing.shared.Stage;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class OrderDto {

    private Long id;
    private String name;
    private String color;
    private String cutter;
    private LocalDate orderedAt;
    private Stage stage;
}
