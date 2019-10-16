package com.frontwit.barcode.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ReaderApplication {
    private static final Logger LOG = LoggerFactory.getLogger(ReaderApplication.class);

    public static void main(final String[] args) {

        SpringApplication.run(ReaderApplication.class, args);
    }
}
