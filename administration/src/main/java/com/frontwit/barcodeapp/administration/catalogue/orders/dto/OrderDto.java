package com.frontwit.barcodeapp.administration.catalogue.orders.dto;

import com.frontwit.barcodeapp.administration.processing.order.model.OrderType;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class OrderDto {

    private Long id;
    private String name;
    private LocalDate orderedAt;
    private LocalDate lastProcessedOn;
    private Stage stage;
    private int quantity;
    private String customer;
    private String route;
    private boolean packed;
    private boolean completed;
    private OrderType type;
}
