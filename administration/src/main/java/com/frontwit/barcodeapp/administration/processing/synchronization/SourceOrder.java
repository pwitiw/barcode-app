package com.frontwit.barcodeapp.administration.processing.synchronization;


import lombok.Data;

import java.util.Date;

@Data
public class SourceOrder {
    private Long id;
    private String nr;
    private String fronts;
    private Date orderedAt;
    private String additionalInfo;
    private String description;
    private String features;
    private String customer;
    private String route;
}
