package com.frontwit.barcode.reader.barcode;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.frontwit.barcode.reader.common.Messages.REGISTERING_HOOK_ERROR;

@Service
public class BarcodeReaderService {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private KeyboardListener keyboardListener;

    public BarcodeReaderService(KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

    @PostConstruct
    public void run() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            logger.warning(REGISTERING_HOOK_ERROR + ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(keyboardListener);
        Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.WARNING);
    }

}