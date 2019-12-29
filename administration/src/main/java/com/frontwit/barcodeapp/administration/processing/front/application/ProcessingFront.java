package com.frontwit.barcodeapp.administration.processing.front.application;

import com.frontwit.barcodeapp.administration.processing.front.application.dto.ProcessFrontCommand;
import com.frontwit.barcodeapp.administration.processing.front.model.Front;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontNotFound;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontRepository;
import com.frontwit.barcodeapp.administration.processing.front.model.ProcessingDetails;
import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.ProcessingException;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvents;
import com.frontwit.barcodeapp.administration.processing.synchronization.FrontSynchronized;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;

import static java.lang.String.format;

@AllArgsConstructor
public class ProcessingFront {

    private FrontRepository frontRepository;
    private DomainEvents domainEvents;

    public void process(ProcessFrontCommand command) {
        var barcode = new Barcode(command.getBarcode());
        frontRepository.findBy(barcode)
                .ifPresentOrElse(front -> process(front, command), () -> publishFrontNotFound(command));
    }

    @EventListener
    public void process(FrontSynchronized event) {
        var barcode = new Barcode(event.getProcessFrontCommand().getBarcode());
        var front = frontRepository.findBy(barcode)
                .orElseThrow(() -> new ProcessingException(format("No front for barcode %s.", barcode)));
        process(front, event.getProcessFrontCommand());
    }

    private void process(Front front, ProcessFrontCommand command) {
        var processingDetails = new ProcessingDetails(Stage.valueOf(command.getStage()), command.getDateTime());
        front.apply(processingDetails).ifPresent(domainEvents::publish);
        frontRepository.save(front);
    }

    private void publishFrontNotFound(ProcessFrontCommand command) {
        domainEvents.publish(new FrontNotFound(new Barcode(command.getBarcode()).getOrderId(), command));
    }
}
