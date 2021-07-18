package com.frontwit.barcodeapp.labels.pdf;

class PdfGenerationException extends RuntimeException {
    PdfGenerationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
