package com.frontwit.barcodeapp.administration.catalogue.orders;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class OrderStatusesDto {
    private Set<Long> ids;
    private boolean completed;
}
