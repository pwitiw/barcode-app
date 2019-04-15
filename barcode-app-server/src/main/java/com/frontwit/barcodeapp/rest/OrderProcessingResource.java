package com.frontwit.barcodeapp.rest;

import com.frontwit.barcodeapp.domain.order.processing.OrderProcessingFacade;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderProcessingResource {

    @Autowired
    OrderProcessingFacade orderProcessingFacade;



}
