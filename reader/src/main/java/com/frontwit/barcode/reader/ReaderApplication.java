package com.frontwit.barcode.reader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@SuppressWarnings("FinalClass")
public class ReaderApplication {
    private ReaderApplication() {
    }

    public static void main(final String[] args) {
        SpringApplication.run(ReaderApplication.class, args);
    }
}
