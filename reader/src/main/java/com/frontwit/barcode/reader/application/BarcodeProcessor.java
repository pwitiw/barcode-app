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

    @Scheduled(fixedRate = 120_000)
    public void publishAllStoredEvents() {
        var published = new HashSet<UUID>();
        var storedBarcodes = storage.findAll();
        try {
            storedBarcodes.forEach((id, command) -> {
                publishBarcode.publish(command);
                published.add(id);
            });
        } catch (PublishingException ignore) {
            LOG.info("Publish attempt failed. Stored barcodes: {}", storedBarcodes.size());
        }
        if (!published.isEmpty()) {
            storage.delete(published);
            LOG.info("Publish attempt succeded. Published barcodes: {}", published.size());
        }
    }

    private Optional<ProcessFrontCommand> createProcessBarcodeCommand(BarcodeScanned event) {
        try {
            var stageId = getStageId(event);
            var barcode = getBarcode(event);
            var command = new ProcessFrontCommand(stageId, barcode, LocalDateTime.now());
            return Optional.of(command);
        } catch (NumberFormatException ex) {
            LOG.warn(format("Barcode contains letters %s", event.getValue()));
            return Optional.empty();
        }
    }

    private Long getBarcode(BarcodeScanned event) {
        String barcode;
        if (event.getStageId() != null) {
            barcode = event.getValue();
        } else {
            barcode = event.getValue().substring(1);
        }
        return Long.valueOf(barcode);
    }

    private Integer getStageId(BarcodeScanned event) {
        var stageId = event.getStageId();
        if (stageId != null) {
            return stageId;
        } else {
            return Integer.valueOf(event.getValue().substring(0, 1));
        }
    }

    private void publishOrStore(ProcessFrontCommand command) {
        try {
            publishBarcode.publish(command);
        } catch (PublishingException ex) {
            storage.store(command);
        }
    }
}
