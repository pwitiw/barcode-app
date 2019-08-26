package com.frontwit.barcodeapp.administration.application.order;

public interface BarcodeConverter {

    long toBarcode(long id);

    long toId(long barcode);
}
