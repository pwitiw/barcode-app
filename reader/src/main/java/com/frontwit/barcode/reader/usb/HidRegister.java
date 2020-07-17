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

public class HidRegister implements HidServicesListener {
    private static final Logger LOG = LoggerFactory.getLogger(ReaderApplication.class);

    private final ConcurrentTaskExecutor concurrentTaskExecutor;
    private final ApplicationEventPublisher eventPublisher;
    private final HidServicesSpecification hidServicesSpecification;
    private final SupportedDevices supportedDevices;
    private final AttachedScanners attachedScanners = new AttachedScanners();

    public HidRegister(ConcurrentTaskExecutor concurrentTaskExecutor,
                       ApplicationEventPublisher eventPublisher,
                       SupportedDevices supportedDevices) {
        this.concurrentTaskExecutor = concurrentTaskExecutor;
        this.eventPublisher = eventPublisher;
        this.supportedDevices = supportedDevices;
        this.hidServicesSpecification = initServicesSpecification();
    }

    @PostConstruct
    void register() {
        var hidServices = HidManager.getHidServices(hidServicesSpecification);
        hidServices.addHidServicesListener(this);
        hidServices.getAttachedHidDevices().stream()
                .filter(supportedDevices::isSupported)
                .forEach(this::addScanner);
    }

    @PreDestroy
    void tearDown() {
        attachedScanners.closeAll();
    }

    @Override
    public void hidDeviceAttached(HidServicesEvent event) {
        var device = event.getHidDevice();
        if (this.supportedDevices.isSupported(device)) {
            this.addScanner(device);
            LOG.info("Attached device: {}", device);
        } else {
            LOG.info("Attached not supported device: {}", device);
        }
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
        LOG.warn("Hid failure, {}", event);
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

    private static HidServicesSpecification initServicesSpecification() {
        HidServicesSpecification hidServicesSpecification = new HidServicesSpecification();
        hidServicesSpecification.setAutoShutdown(true);
        hidServicesSpecification.setScanInterval(500);
        hidServicesSpecification.setPauseInterval(5000);
        hidServicesSpecification.setScanMode(ScanMode.SCAN_AT_FIXED_INTERVAL_WITH_PAUSE_AFTER_WRITE);
        return hidServicesSpecification;
    }
}

