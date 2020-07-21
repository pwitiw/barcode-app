package com.frontwit.barcode.reader.application;

import lombok.Value;

@Value
public class BarcodeScanned {
    private final Integer stageId;
    private final String value;
}
