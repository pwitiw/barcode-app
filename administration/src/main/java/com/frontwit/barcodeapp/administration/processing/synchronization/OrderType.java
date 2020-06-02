package com.frontwit.barcodeapp.administration.processing.synchronization;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OrderType {
    COMPLAINT("Reklamacja"),
    ORDER("Zamówienie");

    static OrderType of(String value) {
        if ("r".equals(value))
            return COMPLAINT;
        else if ("z".equals(value)) {
            return ORDER;
        }
        return ORDER;
    }

    @Getter
    private String displayValue;
}
