package com.frontwit.barcode.reader.usb;

import org.hid4java.HidDevice;

import java.util.ArrayList;
import java.util.List;

public class AttachedScanners {
    private final List<BarcodeScanner> registeredScanners = new ArrayList<>();

    void attach(BarcodeScanner scanner) {
        registeredScanners.add(scanner);
    }

    void closeAll() {
        registeredScanners.forEach(BarcodeScanner::close);
    }

    void detach(HidDevice device) {
        registeredScanners.stream()
                .filter(scanner -> scanner.matches(device))
                .findFirst()
                .ifPresent(scanner -> {
                    scanner.close();
                    registeredScanners.remove(scanner);
                });
    }
}
