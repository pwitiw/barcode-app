package com.frontwit.barcodeapp.administration.order.processing.synchronization;

import com.frontwit.barcodeapp.administration.order.processing.shared.Barcode;

public interface CreateBarcode {

    Barcode create(long orderId, long frontNr);
}
