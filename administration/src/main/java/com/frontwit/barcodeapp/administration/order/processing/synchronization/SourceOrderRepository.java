package com.frontwit.barcodeapp.administration.order.processing.synchronization;

import com.frontwit.barcodeapp.administration.order.processing.shared.OrderId;

import java.util.Optional;

public interface SourceOrderRepository {

    Optional<SourceOrder> findBy(OrderId orderId);

    Dictionary getDictionary();
}
