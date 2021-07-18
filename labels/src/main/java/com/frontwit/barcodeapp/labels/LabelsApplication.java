package com.frontwit.barcodeapp.labels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LabelsApplication {
    public static void main(final String[] args) {
        SpringApplication.run(LabelsApplication.class, args);
    }
}