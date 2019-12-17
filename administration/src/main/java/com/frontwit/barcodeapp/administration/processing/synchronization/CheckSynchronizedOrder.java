package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.processing.shared.OrderId;

public interface CheckSynchronizedOrder {

    boolean isSynchronized(OrderId orderId);
}
