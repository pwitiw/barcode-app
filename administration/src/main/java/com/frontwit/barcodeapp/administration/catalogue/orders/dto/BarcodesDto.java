package com.frontwit.barcodeapp.administration.catalogue.orders.dto;

import lombok.Data;

import java.util.Set;

@Data
public class BarcodesDto {
    private Set<Long> ids;
}
