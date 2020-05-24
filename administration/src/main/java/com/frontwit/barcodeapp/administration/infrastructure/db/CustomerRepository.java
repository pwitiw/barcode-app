package com.frontwit.barcodeapp.administration.infrastructure.db;

import com.frontwit.barcodeapp.administration.processing.synchronization.TargetOrder;

import java.util.Optional;

public interface CustomerRepository {
    Optional<CustomerEntity> findBy(Long id);

    void save(TargetOrder.Customer customerEntity);
}
