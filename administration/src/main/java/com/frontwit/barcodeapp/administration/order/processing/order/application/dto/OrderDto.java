package com.frontwit.barcodeapp.administration.order.processing.order.application.dto;

import com.frontwit.barcodeapp.administration.order.processing.shared.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class OrderDto {

    private Long id;
    private String name;
    private LocalDate orderedAt;
    private Stage stage;
    private int quantity;
}
