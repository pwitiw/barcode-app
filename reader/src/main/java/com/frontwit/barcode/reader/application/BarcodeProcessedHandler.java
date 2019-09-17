package com.frontwit.barcode.reader.application;

import com.frontwit.barcode.reader.barcode.CommandHandler;
import lombok.AllArgsConstructor;

import java.util.logging.Logger;

@AllArgsConstructor
public class BarcodeProcessedHandler implements CommandHandler<ProcessBarcodeCommand> {
    private static final Logger LOGGER = Logger.getLogger(BarcodeProcessedHandler.class.getName());

    private CommandPublisher protocol;

    @Override
    public Class<ProcessBarcodeCommand> getType() {
        return ProcessBarcodeCommand.class;
    }

    @Override
    public void handle(ProcessBarcodeCommand event) {
        protocol.publish(event);
    }
//FIXME remove
//    private void executeTask() {
//        Map<UUID, ProcessBarcodeCommand> barcodeProcesseds = eventStore.findAll();
//        if (protocol.publish(barcodeProcesseds)) {
//            LOGGER.info(String.format(TASK_EXECUTED_SUCCESSFULLY, barcodeProcesseds.size()));
//            eventStore.delete(barcodeProcesseds);
//        } else {
//            LOGGER.info(String.format(TASK_EXECUTED_NOT_SUCCESSFULLY));
//        }
//    }
}