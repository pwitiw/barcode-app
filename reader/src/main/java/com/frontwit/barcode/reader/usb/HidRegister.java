package com.frontwit.barcode.reader.usb;

import com.frontwit.barcode.reader.ReaderApplication;
import org.hid4java.*;
import org.hid4java.event.HidServicesEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static com.frontwit.barcode.reader.usb.BarcodeScanner.PRODUCT_ID;
import static com.frontwit.barcode.reader.usb.BarcodeScanner.VENDOR_ID;

public class HidRegister implements HidServicesListener {
    private static final Logger LOG = LoggerFactory.getLogger(ReaderApplication.class);

    private HidServicesSpecification hidServicesSpecification;
    private ConcurrentTaskExecutor concurrentTaskExecutor;
    private ApplicationEventPublisher eventPublisher;
    private AttachedScanners attachedScanners = new AttachedScanners();

    public HidRegister(ConcurrentTaskExecutor concurrentTaskExecutor, ApplicationEventPublisher eventPublisher) {
        this.concurrentTaskExecutor = concurrentTaskExecutor;
        this.eventPublisher = eventPublisher;
        initServicesSpecification();
    }

    @PostConstruct
    void register() {
        var hidServices = HidManager.getHidServices(hidServicesSpecification);
        hidServices.addHidServicesListener(this);
        hidServices.getAttachedHidDevices().stream()
                .filter(BarcodeScanner::isBarcodeScanner)
                .forEach(this::addScanner);
    }

    @PreDestroy
    void tearDown() {
        attachedScanners.closeAll();
    }

    private void initServicesSpecification() {
        hidServicesSpecification = new HidServicesSpecification();
        hidServicesSpecification.setAutoShutdown(true);
        hidServicesSpecification.setScanInterval(500);
        hidServicesSpecification.setPauseInterval(5000);
        hidServicesSpecification.setScanMode(ScanMode.SCAN_AT_FIXED_INTERVAL_WITH_PAUSE_AFTER_WRITE);
    }

    @Override
    public void hidDeviceAttached(HidServicesEvent event) {
        var device = event.getHidDevice();
        if (device.getVendorId() == VENDOR_ID && device.getProductId() == PRODUCT_ID) {
            addScanner(device);
        }
        LOG.info("Attached {}", device);
    }

    @Override
    public void hidDeviceDetached(HidServicesEvent event) {
        HidDevice device = event.getHidDevice();
        if (device.isOpen()) {
            device.close();
        }
        attachedScanners.detach(device);
        LOG.info("Detached {}", device);
    }

    @Override
    public void hidFailure(HidServicesEvent event) {

    }

    private void addScanner(HidDevice device) {
        if (device.open()) {
            var scanner = new BarcodeScanner(device, eventPublisher::publishEvent);
            attachedScanners.attach(scanner);
            //co w przypadku kilku readerow?
            concurrentTaskExecutor.execute(scanner::listen);
            LOG.info("Device opened successfully {}", device);
        } else {
            LOG.info("Couldn't open device {}", device);
        }
    }
}

