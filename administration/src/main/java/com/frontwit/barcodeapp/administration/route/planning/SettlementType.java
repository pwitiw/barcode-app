package com.frontwit.barcodeapp.administration.route.planning;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
enum SettlementType {
    INVOICE("Faktura VAT"),
    PROOF_OF_PAYMENT("KP"),
    NONE("");

    static SettlementType of(String value) {
        if ("KP".equals(value)) {
            return PROOF_OF_PAYMENT;
        }
        if ("FV".equals(value)) {
            return INVOICE;
        }
        return NONE;
    }

    @Getter
    private String displayValue;
}
