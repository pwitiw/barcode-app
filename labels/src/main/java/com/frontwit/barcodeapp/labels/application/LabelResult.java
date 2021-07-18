package com.frontwit.barcodeapp.labels.application;

import com.amazonaws.util.IOUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayOutputStream;

@RequiredArgsConstructor
public class LabelResult {
    @Getter
    private final Long barcode;
    private final ByteArrayOutputStream output;

    public ByteArrayOutputStream asStream() {
        return output;
    }
}