package com.frontwit.barcode.reader.usb;

import org.hid4java.HidDevice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SupportedDevices {
    private final Map<Short, Integer> devices;

    public SupportedDevices(@Value("#{${devices}}") Map<Short, Integer> devices) {
        this.devices = devices;
    }

    boolean isSupported(HidDevice device) {
        return this.devices.containsKey(device.getVendorId());
    }

    public Integer prefixFor(HidDevice device) {
        return this.devices.get(device.getVendorId());
    }
}
