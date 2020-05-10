package com.frontwit.barcodeapp.administration.route.planning;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
enum SettlementType {
    INVOICE("Faktura VAT"),
    PROOF_OF_PAYMENT("KP");

    static SettlementType of(String value) {
        if ("KP".equals(value)) {
            return PROOF_OF_PAYMENT;
        }
        if ("FV".equals(value)) {
            return INVOICE;
        }
        //TODO we are waiting for settlement type in DB
        return PROOF_OF_PAYMENT;
    }

    @Getter
    private String displayValue;
}
