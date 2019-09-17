package com.frontwit.barcodeapp.administration.order.processing.front.application;

import com.frontwit.barcodeapp.administration.order.processing.front.application.dto.ProcessFrontCommand;
import com.frontwit.barcodeapp.administration.order.processing.front.model.FrontRepository;
import com.frontwit.barcodeapp.administration.order.processing.front.model.ProcessingDetails;
import com.frontwit.barcodeapp.administration.order.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.order.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.order.processing.shared.events.DomainEvents;
import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public class ProcessingFront {

    private FrontRepository frontRepository;
    private DomainEvents domainEvents;

    public void process(ProcessFrontCommand command) {
        var barcode = new Barcode(command.getBarcode());
        var processingDetails = new ProcessingDetails(Stage.valueOf(command.getStage()), command.getDateTime());
        var front = frontRepository.findBy(barcode)
                .orElseThrow(() -> new IllegalStateException(format("No front for barcode %s", command.getBarcode())));
        front.apply(processingDetails).ifPresent(domainEvents::publish);
        frontRepository.save(front);
    }
}
