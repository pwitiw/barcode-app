package com.frontwit.barcodeapp.administration.order.processing.synchronization;


import lombok.Data;

import java.time.LocalDate;
@Data
public class SourceOrder {
    private Long id;
    private String nr;
    private String fronts;
    private LocalDate orderedAt;
    private String additionalInfo;
    private String description;
    private String features;
    private String customer;
}
