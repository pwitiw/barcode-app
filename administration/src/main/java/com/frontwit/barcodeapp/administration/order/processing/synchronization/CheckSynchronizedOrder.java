package com.frontwit.barcodeapp.administration.order.processing.synchronization;

import com.frontwit.barcodeapp.administration.order.processing.shared.OrderId;

public interface CheckSynchronizedOrder {

    boolean isSynchronized(OrderId orderId);
}
