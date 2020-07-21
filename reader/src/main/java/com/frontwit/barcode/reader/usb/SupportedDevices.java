package com.frontwit.barcode.reader.usb;

import org.hid4java.HidDevice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class SupportedDevices {
    private final Map<Short, Short> devices;

    public SupportedDevices(@Value("#{${devices}}") Map<Short, Short> devices) {
        this.devices = devices;
    }

    boolean isSupported(HidDevice device) {
        return Objects.equals(this.devices.get(device.getVendorId()), device.getProductId());
    }
}
