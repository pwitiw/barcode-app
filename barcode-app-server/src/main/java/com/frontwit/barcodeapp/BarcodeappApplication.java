package com.frontwit.barcodeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories
public class BarcodeappApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarcodeappApplication.class, args);
    }
}