package com.frontwit.barcodeapp.administration.processing.order.model;

import com.frontwit.barcodeapp.api.shared.Barcode;
import com.frontwit.barcodeapp.api.shared.Stage;
import lombok.Value;

@Value
public class UpdateStageDetails {

    private final Barcode barcode;
    private final Stage stage;

}
