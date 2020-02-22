package com.frontwit.barcode.reader.usb;

import com.frontwit.barcode.reader.application.BarcodeScanned;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.hid4java.HidDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;


@AllArgsConstructor
class BarcodeScanner {
    private static final Logger LOGGER = LoggerFactory.getLogger(BarcodeScanner.class);
    static final short VENDOR_ID = 0x581;
    static final short PRODUCT_ID = 0x103;

    private static final int LAST_LETTER = 29;
    private static final int FIRST_LETTER = 4;
    private static final int ENTER = 40;
    private static final int ZERO = 39;

    @NonNull
    private HidDevice device;
    @NonNull
    private Consumer<BarcodeScanned> barcodeScannedPublisher;

    void close() {
        if (device.isOpen())
            device.close();
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
                if (data[2] != 0) {
                    if (data[2] >= FIRST_LETTER && data[2] <= LAST_LETTER) {
                        builder.append((char) ('A' + (data[2] - FIRST_LETTER)));
                    } else if (data[2] > LAST_LETTER && data[2] < ZERO) {
                        builder.append(data[2] - LAST_LETTER);
                    } else if (data[2] == ZERO) {
                        builder.append(0);
                    } else if (data[2] == ENTER) {
                        LOGGER.info("Scanned {}", builder.toString());
                        barcodeScannedPublisher.accept(new BarcodeScanned(builder.toString()));
                        builder.setLength(0);
                    }
                }
            } catch (Exception e) {
                LOGGER.warn("Unexpected exception raised", e);
            }
        }
    }

    boolean matches(HidDevice device) {
        return this.device.getPath().equals(device.getPath());
    }

    static boolean isBarcodeScanner(HidDevice device) {
        return device.getVendorId() == VENDOR_ID && device.getProductId() == PRODUCT_ID;
    }
}
