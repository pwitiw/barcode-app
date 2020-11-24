package com.frontwit.barcodeapp.administration.processing.front.application;

import com.frontwit.barcodeapp.administration.processing.front.application.dto.ProcessFrontCommand;
import com.frontwit.barcodeapp.administration.processing.front.model.Front;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontNotFound;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontRepository;
import com.frontwit.barcodeapp.administration.processing.front.model.ProcessingDetails;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvents;
import com.frontwit.barcodeapp.administration.processing.synchronization.FrontSynchronized;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

@AllArgsConstructor
public class FrontProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrontProcessor.class);

    private FrontRepository frontRepository;
    private DomainEvents domainEvents;

    public void process(ProcessFrontCommand command) {
        frontRepository.findBy(command.getBarcode())
                .ifPresentOrElse(
                        front -> process(front, command),
                        () -> publishFrontNotFound(command)
                );
    }

    @EventListener
    public void process(FrontSynchronized event) {
        var command = new ProcessFrontCommand(event.getBarcode(), event.getStage(), event.getDateTime());
        frontRepository.findBy(event.getBarcode())
                .ifPresentOrElse(
                        front -> process(front, command),
                        () -> LOGGER.warn("No front for barcode {}", event.getBarcode().getBarcode()));
    }

    private void process(Front front, ProcessFrontCommand command) {
        var processingDetails = new ProcessingDetails(Stage.valueOf(command.getStage()), command.getDateTime());
        front.apply(processingDetails).forEach(domainEvents::publish);
        frontRepository.save(front);
    }

    private void publishFrontNotFound(ProcessFrontCommand command) {
        domainEvents.publish(new FrontNotFound(command.getBarcode(), command.getStage(), command.getDateTime()));
    }
}
