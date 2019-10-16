package com.frontwit.barcode.reader.usb;

import com.frontwit.barcode.reader.application.BarcodeScanned;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.hid4java.HidDevice;

import java.util.function.Consumer;
import java.util.logging.Logger;


@AllArgsConstructor
class BarcodeScanner {
    private final static Logger LOG = Logger.getLogger(BarcodeScanner.class.getName());

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
        byte[] data = new byte[3];
        if (!device.isOpen()) {
            device.open();
        }
        var builder = new StringBuilder();

        while (device.isOpen()) {
            device.read(data);
            if (data[2] != 0) {
                if (data[2] >= FIRST_LETTER && data[2] <= LAST_LETTER) {
                    builder.append((char) ('A' + (data[2] - FIRST_LETTER)));
                } else if (data[2] > LAST_LETTER && data[2] < ZERO) {
                    builder.append(data[2] - LAST_LETTER);
                } else if (data[2] == ZERO) {
                    builder.append(0);
                } else if (data[2] == ENTER) {
                    LOG.info("Scanned: " + builder.toString());
                    barcodeScannedPublisher.accept(new BarcodeScanned(builder.toString()));
                    builder.setLength(0);
                }
            }
        }
    }
}
