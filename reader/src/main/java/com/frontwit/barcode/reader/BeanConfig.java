package com.frontwit.barcode.reader;

import com.frontwit.barcode.reader.application.BarcodeProcessor;
import com.frontwit.barcode.reader.application.BarcodeStorage;
import com.frontwit.barcode.reader.application.PublishBarcode;
import com.frontwit.barcode.reader.store.SQLiteBarcodeStorage;
import com.frontwit.barcode.reader.store.SQLiteRepository;
import com.frontwit.barcode.reader.usb.HidRegister;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class BeanConfig {

    @Bean
    SQLiteRepository sqLiteRepository() {
        return new SQLiteRepository();
    }

    @Bean
    BarcodeStorage barcodeStorage(SQLiteRepository repository) {
        return new SQLiteBarcodeStorage(repository);
    }

    @Bean
    BarcodeProcessor barcodeProcessor(PublishBarcode publishBarcode, BarcodeStorage storage) {
        return new BarcodeProcessor(publishBarcode, storage);
    }

    @Bean
    public ConcurrentTaskExecutor getAsyncExecutor() {
        return new ConcurrentTaskExecutor(Executors.newFixedThreadPool(10));
    }

    @Bean
    HidRegister hidRegister(ConcurrentTaskExecutor taskExecutor, ApplicationEventPublisher eventPublisher) {
        return new HidRegister(taskExecutor, eventPublisher);
    }
}
