package com.frontwit.barcode.reader.infrastructure.listener;

import javax.usb.UsbException;
import javax.usb.UsbInterface;

public class BarcodeReaderDevice {
    private final UsbInterface iface;

    private final byte inEndpoint;

    public BarcodeReaderDevice(UsbInterface iface, byte inEndpoint) {
        if (iface == null)
            throw new IllegalArgumentException("iface must be set");
        this.iface = iface;
        this.inEndpoint = inEndpoint;
    }

    public void open() {
        try {
            iface.claim();
        } catch (UsbException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            iface.release();
        } catch (UsbException e) {
            e.printStackTrace();
        }
    }
}
