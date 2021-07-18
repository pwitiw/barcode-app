package com.frontwit.barcodeapp.administration.catalogue.orders.dto;

import com.frontwit.barcodeapp.administration.processing.order.model.OrderType;
import com.frontwit.barcodeapp.api.shared.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@SuppressWarnings("PMD.TooManyFields")
public class OrderDetailDto {

    private Long id;
    private String name;
    private String color;
    private String size;
    private String cutter;
    private String comment;
    private String customer;
    private String route;
    private Stage stage;
    private LocalDate orderedAt;
    private List<FrontDto> fronts;
    private boolean completed;
    private boolean packed;
    private Long deadline;
    private BigDecimal valuation;
    private OrderType type;
}
