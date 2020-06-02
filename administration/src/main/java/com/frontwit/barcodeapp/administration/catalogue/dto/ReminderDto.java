package com.frontwit.barcodeapp.administration.catalogue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReminderDto {
    String name;
    String customer;
    long deadline;
}