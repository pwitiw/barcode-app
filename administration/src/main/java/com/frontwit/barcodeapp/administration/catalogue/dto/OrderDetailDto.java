package com.frontwit.barcodeapp.administration.catalogue.dto;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDetailDto {

    private Long id;
    private String name;
    private String color;
    private String size;
    private String cutter;
    private String comment;
    private String customer;
    private Stage stage;
    private LocalDate orderedAt;
    private List<FrontDto> fronts;
}
