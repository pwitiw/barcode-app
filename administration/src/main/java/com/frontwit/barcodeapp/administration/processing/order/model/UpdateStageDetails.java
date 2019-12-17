package com.frontwit.barcodeapp.administration.processing.order.model;

import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import lombok.Value;

@Value
public class UpdateStageDetails {

    private Barcode barcode;
    private Stage stage;

}