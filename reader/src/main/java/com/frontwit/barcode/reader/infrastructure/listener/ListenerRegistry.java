package com.frontwit.barcode.reader.infrastructure.listener;

import lombok.AllArgsConstructor;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
@Component
class ListenerRegistry {

    private final static Logger LOGGER = Logger.getLogger(ListenerRegistry.class.getName());

    private BarcodeListener barcodeListener;

    @PostConstruct
    void run() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            LOGGER.warning("Error while registering native hook:" + ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(barcodeListener);
        Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.WARNING);
        LOGGER.info("Barcode listener registered");
    }
}

