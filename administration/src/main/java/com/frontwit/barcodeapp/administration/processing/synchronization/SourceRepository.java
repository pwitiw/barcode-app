package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerEntity;
import com.frontwit.barcodeapp.administration.processing.shared.OrderId;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SourceRepository {

    Optional<SourceOrder> findBy(OrderId orderId);

    Dictionary getDictionary();

    List<SourceOrder> findByDateBetween(Instant from);

    List<CustomerEntity> findCustomers();
}
