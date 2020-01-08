package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.processing.shared.OrderId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SourceOrderRepository {

    Optional<SourceOrder> findBy(OrderId orderId);

    Dictionary getDictionary();

    List<SourceOrder> findByDateBetween(LocalDateTime from, LocalDateTime to);
}
