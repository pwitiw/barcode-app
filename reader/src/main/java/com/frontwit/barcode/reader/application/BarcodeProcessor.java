package com.frontwit.barcode.reader.application;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import javax.ws.rs.ProcessingException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class BarcodeProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(BarcodeProcessor.class.getName());

    private PublishBarcode publishBarcode;

    private BarcodeStorage storage;


    @EventListener
    void acceptScannedBarcode(BarcodeScanned event) {
        createProcessBarcodeCommand(event).ifPresent(this::publishOrStore);
    }

    @EventListener
    void publishAllStoredEvents(ConnectedToMqtt event) {
        var published = new HashSet<UUID>();
        storage.findAll().forEach((id, command) -> {
            try {
                publishBarcode.publish(command);
                published.add(id);
            } catch (ProcessingException ignore) {
            }
        });
        storage.delete(published);
    }

    private Optional<ProcessBarcodeCommand> createProcessBarcodeCommand(BarcodeScanned event) {
        try {
            var stageId = Integer.valueOf(event.getValue().substring(0, 1));
            var barcode = Long.valueOf(event.getValue().substring(1));
            var command = new ProcessBarcodeCommand(stageId, barcode, LocalDateTime.now());
            return Optional.of(command);
        } catch (NumberFormatException ex) {
            LOG.warn("Number parsing error for: " + event.getValue());
        }
        return Optional.empty();
    }

    private void publishOrStore(ProcessBarcodeCommand command) {
        try {
            publishBarcode.publish(command);
        } catch (ProcessingException ex) {
            LOG.warn("Error while publishing.");
            storage.store(command);
        }
    }
}