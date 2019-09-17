package com.frontwit.barcode.reader.infrastructure;

import com.frontwit.barcode.reader.application.BarcodeProcessedHandler;
import com.frontwit.barcode.reader.application.CommandPublisher;
import com.frontwit.barcode.reader.barcode.CommandGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class BarCodeBeanConfiguration {

    @Bean
    public BarcodeProcessedHandler barcodeProcessedHandler(CommandPublisher commandPublisher){
        return new BarcodeProcessedHandler(commandPublisher);
    }

    @Bean
    public CommandGateway commandGateway(BarcodeProcessedHandler barcodeProcessedHandler) {
        CommandGateway commandGateway = new CommandGateway();
        commandGateway.register(barcodeProcessedHandler);
        return commandGateway;
    }

    @Bean
    public TaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setThreadNamePrefix("Scheduler");
        return threadPoolTaskScheduler;
    }
}
