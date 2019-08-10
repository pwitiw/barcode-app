package com.frontwit.barcode.reader.barcode;

import com.frontwit.barcode.reader.barcode.storage.BarcodeStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.Collection;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

import static com.frontwit.barcode.reader.common.Messages.TASK_EXECUTED_NOT_SUCCESSFULLY;
import static com.frontwit.barcode.reader.common.Messages.TASK_EXECUTED_SUCCESSFULLY;

@Component
public class BarcodeHandler implements CommandHandler<BarcodeCommand> {

    private static final Logger LOGGER = Logger.getLogger(BarcodeHandler.class.getName());

    @Value("${task-execution-delay}")
    private Integer executionDelay;

    private BarcodeRestClient rest;

    private BarcodeStorage barcodeStorage;

    private TaskScheduler scheduler;

    private ScheduledFuture<?> task;


    public BarcodeHandler(BarcodeRestClient rest, BarcodeStorage barcodeStorage, TaskScheduler scheduler) {
        this.rest = rest;
        this.barcodeStorage = barcodeStorage;
        this.scheduler = scheduler;
    }

    @Override
    public Class<BarcodeCommand> getType() {
        return BarcodeCommand.class;
    }

    @Override
    public void handle(BarcodeCommand command) {
        barcodeStorage.save(command);
        LOGGER.info(String.format("Persisted %s", command));
        if (task == null || task.isDone()) {
            Long scheduledTime = Instant.now().toEpochMilli() + executionDelay;
            task = scheduler.schedule(this::executeTask, Date.from(Instant.ofEpochMilli(scheduledTime)));
            LOGGER.info("Task scheduled.");
        }
    }

    private void executeTask() {
        Collection<BarcodeCommand> barcodeCommands = barcodeStorage.findAll();
        if (rest.sendBarCode(barcodeCommands)) {
            LOGGER.info(String.format(TASK_EXECUTED_SUCCESSFULLY, barcodeCommands.size()));
            barcodeStorage.delete(barcodeCommands);
        } else {
            LOGGER.info(String.format(TASK_EXECUTED_NOT_SUCCESSFULLY));
        }
    }
}