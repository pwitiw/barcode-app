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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.frontwit.barcode.reader.usb.BarcodeScanner.PRODUCT_ID;
import static com.frontwit.barcode.reader.usb.BarcodeScanner.VENDOR_ID;

public class HidRegister implements HidServicesListener {
    private static final Logger LOG = LoggerFactory.getLogger(ReaderApplication.class);

    private HidServicesSpecification hidServicesSpecification;
    private ConcurrentTaskExecutor concurrentTaskExecutor;
    private ApplicationEventPublisher eventPublisher;
    private List<BarcodeScanner> registeredScanners = new ArrayList<>();

    public HidRegister(ConcurrentTaskExecutor concurrentTaskExecutor, ApplicationEventPublisher eventPublisher) {
        this.concurrentTaskExecutor = concurrentTaskExecutor;
        this.eventPublisher = eventPublisher;
        initServicesSpecification();
    }

    @PostConstruct
    void register() {
        var hidServices = HidManager.getHidServices(hidServicesSpecification);
        hidServices.addHidServicesListener(this);

        // todo co w przypadku wielu urzadzen
        var device = hidServices.getHidDevice(VENDOR_ID, BarcodeScanner.PRODUCT_ID, null);

        Optional.ofNullable(device).ifPresentOrElse(this::addScanner, () -> LOG.warn("No device plugged or no permissions."));
    }

    @PreDestroy
    void tearDown() {
        registeredScanners.forEach(BarcodeScanner::close);
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
        LOG.info("Attached: " + event.getHidDevice());
    }

    @Override
    public void hidDeviceDetached(HidServicesEvent event) {
        HidDevice device = event.getHidDevice();
        if (device.isOpen()) {
            device.close();
        }
        LOG.info("Detached: " + event.getHidDevice());
    }

    @Override
    public void hidFailure(HidServicesEvent event) {

    }

    private void addScanner(HidDevice device) {
        var scanner = new BarcodeScanner(device, eventPublisher::publishEvent);
        registeredScanners.add(scanner);
        concurrentTaskExecutor.execute(scanner::listen);
    }
}

