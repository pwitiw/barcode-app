package com.frontwit.barcodeapp.administration.processing.application;

import com.frontwit.barcodeapp.administration.processing.application.dto.ProcessFrontDto;
import com.frontwit.barcodeapp.administration.processing.application.readmodel.OrderDetailsView;
import com.frontwit.barcodeapp.administration.processing.application.readmodel.ProcessingQueryFacade;
import com.frontwit.barcodeapp.administration.processing.domain.Barcode;
import com.frontwit.barcodeapp.administration.processing.domain.ProcessFront;
import com.frontwit.barcodeapp.administration.processing.domain.Status;
import lombok.AllArgsConstructor;

import java.util.Collections;

@AllArgsConstructor
public class ProcessingFacade {

    private FrontProcessingService frontProcessingService;

    private ProcessingQueryFacade processingQueryFacade;

    public void processFront(ProcessFrontDto command) {
        Barcode barcode = new Barcode(command.getBarcode());
        Status status = Status.valueOf(command.getStatus());
        frontProcessingService.process(new ProcessFront(barcode, status, command.getDateTime()));
    }

    public OrderDetailsView detailsFor(Long orderId) {
        return new OrderDetailsView(Status.MILLING, Collections.emptyList());
    }
}
