package com.frontwit.barcodeapp.administration.catalogue.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReminderDto {
    private String name;
    private String customer;
    private long deadline;
}
