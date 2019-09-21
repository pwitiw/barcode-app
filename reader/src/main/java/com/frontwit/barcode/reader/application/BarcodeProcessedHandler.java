package com.frontwit.barcode.reader.application;

import com.frontwit.barcode.reader.barcode.CommandHandler;
import lombok.AllArgsConstructor;

import java.util.logging.Logger;

@AllArgsConstructor
public class BarcodeProcessedHandler implements CommandHandler<ProcessBarcodeCommand> {
    private static final Logger LOGGER = Logger.getLogger(BarcodeProcessedHandler.class.getName());

    private CommandPublisher publisher;

    @Override
    public Class<ProcessBarcodeCommand> getType() {
        return ProcessBarcodeCommand.class;
    }

    @Override
    public void handle(ProcessBarcodeCommand event) {
        publisher.publish(event);
    }
//FIXME remove
//    private void executeTask() {
//        Map<UUID, ProcessBarcodeCommand> barcodeProcesseds = eventStore.findAll();
//        if (publisher.publish(barcodeProcesseds)) {
//            LOGGER.info(String.format(TASK_EXECUTED_SUCCESSFULLY, barcodeProcesseds.size()));
//            eventStore.delete(barcodeProcesseds);
//        } else {
//            LOGGER.info(String.format(TASK_EXECUTED_NOT_SUCCESSFULLY));
//        }
//    }
}