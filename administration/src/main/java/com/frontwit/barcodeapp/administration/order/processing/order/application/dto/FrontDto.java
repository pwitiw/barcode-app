package com.frontwit.barcodeapp.administration.order.processing.order.application.dto;

import com.frontwit.barcodeapp.administration.order.processing.front.model.ProcessingDetails;
import com.frontwit.barcodeapp.administration.order.processing.shared.Stage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class FrontDto {
    private long barcode;
    private int height;
    private int width;
    private int quantity;
    private Stage stage;
    private String comment;
    private Set<ProcessingDetails> processings;
    private Set<ProcessingDetails> amendments;
}
