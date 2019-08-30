package com.frontwit.barcodeapp.administration.processing.application;

import com.frontwit.barcodeapp.administration.processing.application.dto.ProcessFrontDto;
import com.frontwit.barcodeapp.administration.processing.application.readmodel.OrderDetailsView;
import com.frontwit.barcodeapp.administration.processing.application.readmodel.ProcessingQueryFacade;
import com.frontwit.barcodeapp.administration.processing.model.Barcode;
import com.frontwit.barcodeapp.administration.processing.model.ProcessFront;
import com.frontwit.barcodeapp.administration.processing.model.Stage;
import lombok.AllArgsConstructor;

import java.util.Collections;

@AllArgsConstructor
public class ProcessingFacade {

    private FrontProcessingService frontProcessingService;

    private ProcessingQueryFacade processingQueryFacade;

    public void processFront(ProcessFrontDto command) {
        Barcode barcode = new Barcode(command.getBarcode());
        Stage stage = Stage.valueOf(command.getStatus());
        frontProcessingService.process(new ProcessFront(barcode, stage, command.getDateTime()));
    }

    public OrderDetailsView detailsFor(Long orderId) {
        return new OrderDetailsView(Stage.MILLING, Collections.emptyList());
    }
}
