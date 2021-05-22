package com.frontwit.barcodeapp.administration.domain.qr;

import com.frontwit.barcodeapp.administration.catalogue.orders.barcodes.BarcodePdf;

public interface QrCodePrinter {
    void print(BarcodePdf pdf);
}
