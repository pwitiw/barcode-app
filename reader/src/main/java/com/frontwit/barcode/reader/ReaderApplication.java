package com.frontwit.barcode.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.usb.UsbDevice;
import javax.usb.UsbException;

@SpringBootApplication
public class ReaderApplication {
    private static final Logger LOG = LoggerFactory.getLogger(ReaderApplication.class);

    public static void main(final String[] args) throws UsbException {
//        final UsbServices services = UsbHostManager.getUsbServices();
//        services.addUsbServicesListener(new UsbServicesListener() {
//            @Override
//            public void usbDeviceAttached(UsbServicesEvent event) {
//                if (isBarcodeReader(event.getUsbDevice())) {
//                    LOG.error("PODLACZONO CZYTNIK");
//                    LOG.error(event.getUsbDevice().toString());
//                    UsbConfiguration config = event.getUsbDevice().getActiveUsbConfiguration();
//                    for (UsbInterface iface : (List<UsbInterface>) config.getUsbInterfaces()) {
//                        List<UsbEndpoint> endpoints = iface.getUsbEndpoints();
//                        if (!endpoints.isEmpty()) {
//                            UsbEndpointDescriptor ed1 =
//                                    endpoints.get(0).getUsbEndpointDescriptor();
//                            var endpointAddress = ed1.bEndpointAddress();
//                            System.out.println(endpointAddress);
//                            var barcodeReaderDevice = new BarcodeReaderDevice(iface, ed1.bEndpointAddress());
//                            barcodeReaderDevice.open();
//                            barcodeReaderDevice.close();
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void usbDeviceDetached(UsbServicesEvent event) {
//                if (isBarcodeReader(event.getUsbDevice())) {
//                    LOG.error("ODLACZONO");
//                }
//            }
//        });

        SpringApplication.run(ReaderApplication.class, args);
    }

    public static boolean isBarcodeReader(UsbDevice device) {
        return device.getUsbDeviceDescriptor().idVendor() == 1409;
    }
}
