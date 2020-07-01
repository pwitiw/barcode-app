package com.frontwit.barcodeapp.administration.route.planning;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
enum SettlementType {
    INVOICE("Faktura VAT"),
    PROOF_OF_PAYMENT("KP");

    @Getter
    private String displayValue;

    static SettlementType of(String value) {
        if ("FV".equals(value)) {
            return INVOICE;
        }
        //TODO we are waiting for settlement type in DB
        return PROOF_OF_PAYMENT;
    }
}
