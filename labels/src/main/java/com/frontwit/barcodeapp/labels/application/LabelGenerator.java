package com.frontwit.barcodeapp.labels.application;

import com.frontwit.barcodeapp.api.shared.Barcode;

public interface LabelGenerator {
    LabelResult createMessage(Barcode barcode, String message);

    LabelResult create(LabelInfo labelInfo);
}
