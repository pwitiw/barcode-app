package com.frontwit.barcode.reader.usb;

import com.frontwit.barcode.reader.application.BarcodeScanned;
import lombok.NonNull;
import org.hid4java.HidDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;


@SuppressWarnings({"DeclarationOrder", "PMD.AvoidUsingShortType"})
class BarcodeScanner {
    private static final Logger LOGGER = LoggerFactory.getLogger(BarcodeScanner.class);
    private static final int LAST_LETTER = 29;
    private static final int FIRST_LETTER = 4;
    private static final int ENTER = 40;
    private static final int ZERO = 39;

    private final HidDevice device;
    private final Consumer<BarcodeScanned> barcodeScannedPublisher;
    private final Integer prefix;

    BarcodeScanner(@NonNull HidDevice device, @NonNull Consumer<BarcodeScanned> barcodeScannedPublisher, Integer prefix) {
        this.device = device;
        this.barcodeScannedPublisher = barcodeScannedPublisher;
        this.prefix = prefix;
    }

    void close() {
        if (device.isOpen()) {
            device.close();
        }
    }

    void listen() {
        if (!device.isOpen()) {
            device.open();
        }
        byte[] data = new byte[3];
        var builder = new StringBuilder();
        while (device.isOpen()) {
            try {
                device.read(data);
                if (isNotEmpty(data)) {
                    applyScannedChar(data, builder);
                }
            } catch (Exception e) {
                LOGGER.warn("Unexpected exception raised", e);
            }
        }
    }

    private void applyScannedChar(byte[] data, StringBuilder builder) {
        if (data[2] >= FIRST_LETTER && data[2] <= LAST_LETTER) {
            builder.append((char) ('A' + (data[2] - FIRST_LETTER)));
        } else if (data[2] > LAST_LETTER && data[2] < ZERO) {
            builder.append(data[2] - LAST_LETTER);
        } else if (data[2] == ZERO) {
            builder.append(0);
        } else if (data[2] == ENTER) {
            LOGGER.info("Scanned {}", builder.toString());
            barcodeScannedPublisher.accept(new BarcodeScanned(prefix, builder.toString()));
            builder.setLength(0);
        }
    }

    private boolean isNotEmpty(byte[] data) {
        return data[2] != 0;
    }

    boolean matches(HidDevice device) {
        return this.device.getPath().equals(device.getPath());
    }

}
