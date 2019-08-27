package com.frontwit.barcodeapp.administration.processing.domain;

interface ProcessingPolicy {

    ProcessingPolicy ALWAYS_ALLOW = () -> {
    };

    void verify();
}
