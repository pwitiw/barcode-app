package com.frontwit.barcodeapp.administration.infrastructure.pdf;

class PdfGenerationException extends RuntimeException {
    PdfGenerationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
