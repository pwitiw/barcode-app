package com.frontwit.barcodeapp.administration.domain.qr;

import com.frontwit.barcodeapp.administration.catalogue.orders.barcodes.BarcodePdf;
import com.frontwit.barcodeapp.administration.processing.shared.Barcode;

public interface QrCodeGenerator {
    BarcodePdf createMessage(Barcode barcode, String message);

    BarcodePdf create(QrCodeInfo qrCodeInfo);
}
