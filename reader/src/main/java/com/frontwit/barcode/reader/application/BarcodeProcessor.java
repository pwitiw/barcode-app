package com.frontwit.barcode.reader.application;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@AllArgsConstructor
public class BarcodeProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(BarcodeProcessor.class.getName());

    private PublishBarcode publishBarcode;

    private BarcodeStorage storage;

    @EventListener
    public void acceptScannedBarcode(BarcodeScanned event) {
        createProcessBarcodeCommand(event).ifPresent(this::publishOrStore);
    }

    @Scheduled(fixedRate = 30000)
    public void publishAllStoredEvents() {
        var published = new HashSet<UUID>();
        try {
            storage.findAll().forEach((id, command) -> {
                publishBarcode.publish(command);
                published.add(id);
            });
        } catch (PublishingException ignore) {
        }
        if (!published.isEmpty()) {
            storage.delete(published);
        }
    }

    private Optional<ProcessBarcodeCommand> createProcessBarcodeCommand(BarcodeScanned event) {
        try {
            var stageId = Integer.valueOf(event.getValue().substring(0, 1));
            var barcode = Long.valueOf(event.getValue().substring(1));
            var command = new ProcessBarcodeCommand(stageId, barcode, LocalDateTime.now());
            return Optional.of(command);
        } catch (NumberFormatException ex) {
            LOG.warn(format("Barcode contains letters %s -> deleted letters", event.getValue()));
            var stageId = Integer.valueOf(event.getValue().substring(0, 1));
            var barcode = event.getValue().substring(1);
            barcode = barcode.replaceAll("[A-Za-z]", "");
            var command = new ProcessBarcodeCommand(stageId, Long.valueOf(barcode), LocalDateTime.now());
            return Optional.of(command);
        }
    }

    private void publishOrStore(ProcessBarcodeCommand command) {
        try {
            publishBarcode.publish(command);
        } catch (PublishingException ex) {
            storage.store(command);
        }
    }
}