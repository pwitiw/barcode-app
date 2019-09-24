package com.frontwit.barcode.reader.infrastructure.listener;

import lombok.AllArgsConstructor;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.DefaultDispatchService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.*;

@AllArgsConstructor
@Component
class ListenerRegistry {

    private final static Logger LOGGER = Logger.getLogger(ListenerRegistry.class.getName());
    private static final Logger globalScreenLogger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
    private BarcodeListener barcodeListener;

    @PostConstruct
    void run() {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.setEventDispatcher(new DefaultDispatchService());
        } catch (NativeHookException ex) {
            LOGGER.warning("Error while registering native hook:" + ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(barcodeListener);
        Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.WARNING);
        LOGGER.info("Barcode listener registered");
    }

    private void setUpLogger(){

        // Disable parent logger and set the desired level.
        globalScreenLogger.setUseParentHandlers(false);
        globalScreenLogger.setLevel(Level.ALL);

        // Add our custom formatter to a console handler.
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new LogFormatter());
        handler.setLevel(Level.ALL);
        globalScreenLogger.addHandler(handler);

    }



    private final class LogFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            StringBuilder line = new StringBuilder();

            line.append(new Date(record.getMillis()))
                    .append(" ")
                    .append(record.getLevel().getLocalizedName())
                    .append(":\t")
                    .append(formatMessage(record));

            if (record.getThrown() != null) {
                try {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    record.getThrown().printStackTrace(pw);
                    pw.close();
                    line.append(sw.toString());
                    sw.close();
                }
                catch (Exception ex) { /* Do Nothing */ }
            }

            return line.toString();
        }
    }
}

