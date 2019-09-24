package com.frontwit.barcode.reader.infrastructure.listener;

import com.frontwit.barcode.reader.application.ProcessBarcodeCommand;
import com.frontwit.barcode.reader.barcode.CommandGateway;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import static org.jnativehook.keyboard.NativeKeyEvent.VC_0;
import static org.jnativehook.keyboard.NativeKeyEvent.VC_1;

@Component
public class BarcodeListener implements NativeKeyListener {
    private static final Logger LOGGER = Logger.getLogger(BarcodeListener.class.getName());

    private CommandGateway commandGateway;
    private final StringBuilder input = new StringBuilder();

    public BarcodeListener(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {

        int code = event.getKeyCode();
        if (isNumber(code)) {
            input.append(NativeKeyEvent.getKeyText(event.getKeyCode()));
        } else if (isEnter(code)) {
            if (hasMinimumLength()) {
                emitCommand();
            }
            input.setLength(0);
        } else if (isExit(code)) {
            unregister();
        } else {
            input.setLength(0);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent event) {
        // do nothing
    }

    public void nativeKeyTyped(NativeKeyEvent event) {
        // do nothing
    }

    private boolean isNumber(int code) {
        return code >= VC_1 && code <= VC_0;
    }

    private boolean isEnter(int code) {
        return code == NativeKeyEvent.VC_ENTER;
    }

    private boolean isExit(int code) {
        return code == NativeKeyEvent.VC_ESCAPE;
    }

    private boolean hasMinimumLength() {
        return input.toString().length() > 3;
    }

    private void emitCommand() {
        String inputString = input.toString();
        Integer readerId = Integer.valueOf(inputString.substring(0, 1));
        Long barcode = Long.valueOf(inputString.substring(1));
        commandGateway.fire(new ProcessBarcodeCommand(readerId, barcode, LocalDateTime.now()));
        LOGGER.info(input.toString());
    }

    private void unregister() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e1) {
            LOGGER.warning(e1.getMessage());
        }
    }

}