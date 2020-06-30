package com.frontwit.barcodeapp.administration.processing.order.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import lombok.Value;

@Value
public class UpdateStageDetails {

    private final Barcode barcode;
    private final Stage stage;

}
