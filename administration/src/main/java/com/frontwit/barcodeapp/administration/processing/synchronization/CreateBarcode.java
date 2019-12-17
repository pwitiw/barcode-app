package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;

public interface CreateBarcode {

    Barcode create(long orderId, long frontNr);
}
