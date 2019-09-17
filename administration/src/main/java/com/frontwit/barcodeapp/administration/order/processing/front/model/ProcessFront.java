package com.frontwit.barcodeapp.administration.order.processing.front.model;

import com.frontwit.barcodeapp.administration.order.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.order.processing.shared.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProcessFront {
    private Barcode barcode;
    private Stage stage;
    private LocalDateTime dateTime;
}
