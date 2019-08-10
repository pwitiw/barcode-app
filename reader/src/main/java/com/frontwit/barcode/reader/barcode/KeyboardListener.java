package com.frontwit.barcode.reader.barcode;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static org.jnativehook.keyboard.NativeKeyEvent.VC_0;
import static org.jnativehook.keyboard.NativeKeyEvent.VC_1;

@Component
public class KeyboardListener implements NativeKeyListener {
    private static final Logger LOGGER = Logger.getLogger(KeyboardListener.class.getName());

    private CommandGateway commandGateway;
    private final StringBuilder input = new StringBuilder();

    public KeyboardListener(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
        int code = event.getKeyCode();
        if (isNumber(code)) {
            input.append(NativeKeyEvent.getKeyText(event.getKeyCode()));
        } else if (isEnter(code) && !input.toString().isEmpty()) {
            emitCommand();
        } else if (isExit(code)) {
            unregister();
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

    private void emitCommand() {
        String inputString = input.toString();
        Integer readerId = Integer.valueOf(inputString.substring(0, 1));
        Long barcode = Long.valueOf(inputString.substring(1));
        commandGateway.fire(new BarcodeCommand(readerId, barcode));
        LOGGER.info(input.toString());
        input.setLength(0);
    }

    private void unregister() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e1) {
            LOGGER.warning(e1.getMessage());
        }
    }

}