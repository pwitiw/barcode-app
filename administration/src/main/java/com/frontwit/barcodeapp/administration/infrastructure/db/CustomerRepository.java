package com.frontwit.barcodeapp.administration.infrastructure.db;

import com.frontwit.barcodeapp.administration.processing.synchronization.SourceCustomer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<CustomerEntity> findBy(Long id);

    void save(SourceCustomer customer);
}
