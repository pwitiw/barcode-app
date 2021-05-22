package com.frontwit.barcodeapp.administration.domain.qr;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;

import java.util.Optional;

public interface QrCodeInfoProvider {
    Optional<QrCodeInfo> find(Barcode barcode);
}
