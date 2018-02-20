package com.frontwit.barcodeapp.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Order {

    @Id
    private Long id;

    private String name;

    private List<Component> components;

    private LocalDate orderDate;
}
