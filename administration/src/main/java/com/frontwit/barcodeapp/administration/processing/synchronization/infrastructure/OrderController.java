package com.frontwit.barcodeapp.administration.processing.synchronization.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OrderController {

    @PostMapping(value = "/orders/synchronize")
    public void synchronize(){

    }
}
