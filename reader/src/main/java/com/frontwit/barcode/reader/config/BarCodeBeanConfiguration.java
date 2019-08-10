package com.frontwit.barcode.reader.config;

import com.frontwit.barcode.reader.barcode.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class BarCodeBeanConfiguration {

    @Bean
    public CommandGateway commandGateway(BarcodeHandler barcodeHandler) {
        CommandGateway commandGateway = new CommandGateway();
        commandGateway.register(barcodeHandler);
        return commandGateway;
    }

    @Bean
    public TaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setThreadNamePrefix("Scheduler");
        return threadPoolTaskScheduler;
    }
}
