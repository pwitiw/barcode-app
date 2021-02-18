package com.frontwit.barcodeapp.administration.processing.front.model;

import com.frontwit.barcodeapp.administration.processing.shared.ProcessingException;

public class ProcessingPolicyViolationException extends ProcessingException {
    ProcessingPolicyViolationException(String msg) {
        super(msg);
    }
}
