package com.frontwit.barcodeapp.administration.order.processing.order.model;

import com.frontwit.barcodeapp.administration.order.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.order.processing.shared.Stage;
import lombok.Value;

@Value
public class UpdateStageDetails {

    private Barcode barcode;
    private Stage stage;

}