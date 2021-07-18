package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.api.shared.OrderId;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SourceRepository {

    Optional<SourceOrder> findBy(OrderId orderId);

    Dictionary getDictionary();

    List<SourceOrder> findByDateBetween(Instant from, Instant to);

    List<SourceCustomer> findCustomers();
}
