package com.frontwit.barcodeapp.logic;

public class IllegalProcessingOrderException extends RuntimeException {

    public IllegalProcessingOrderException(String message) {
        super(message);
    }
}
