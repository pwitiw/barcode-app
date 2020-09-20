package com.frontwit.barcodeapp.administration.processing.synchronization.infrastructure;

import com.frontwit.barcodeapp.administration.processing.synchronization.CustomerSynchronizer;
import com.frontwit.barcodeapp.administration.processing.synchronization.OrderSynchronizer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SynchronizationController {

    private final OrderSynchronizer orderSynchronizer;
    private final CustomerSynchronizer customerSynchronizer;

    @PostMapping("/orders/synchronize")
    public long synchronize() {
        customerSynchronizer.synchronize();
        return orderSynchronizer.synchronize();
    }
}
