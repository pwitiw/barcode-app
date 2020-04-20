package com.frontwit.barcodeapp.administration.catalogue.dto;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class OrderDto {

    private Long id;
    private String name;
    private LocalDate lastProcessedOn;
    private Stage stage;
    private int quantity;
    private String customer;
    private String route;
    private boolean packed;
}
