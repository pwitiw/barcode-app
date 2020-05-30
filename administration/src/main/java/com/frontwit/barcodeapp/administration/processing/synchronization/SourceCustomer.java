package com.frontwit.barcodeapp.administration.processing.synchronization;

import lombok.Data;

@Data
public class SourceCustomer {

    private Long id;
    private String name;
    private String address;
    private String route;
    private String phoneNumber;
}
