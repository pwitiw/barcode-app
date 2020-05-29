package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CustomerSynchronizer {
    private CustomerRepository customerRepository;
    private SourceRepository sourceRepository;

    public void synchronize() {
        List<SourceCustomer> customers = sourceRepository.findCustomers();
        customers.forEach(this::saveCustomers);
    }

    private void saveCustomers(SourceCustomer customer) {
        customerRepository.save(customer);
    }
}
