package com.frontwit.barcodeapp.administration.processing.synchronization.infrastructure;

import com.frontwit.barcodeapp.administration.processing.synchronization.OrderSynchronizer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SynchronizationController {

    private OrderSynchronizer orderSynchronizer;
    private CustomerSynchronizer customerSynchronizer;

    @PostMapping(value = "/orders/synchronize")
    public long synchronize() {
        customerSynchronizer.synchronizeCustomers();
        return orderSynchronizer.synchronizeOrders();
    }
}
