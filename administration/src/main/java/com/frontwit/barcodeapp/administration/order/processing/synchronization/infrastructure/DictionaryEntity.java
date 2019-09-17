package com.frontwit.barcodeapp.administration.order.processing.synchronization.infrastructure;

import com.frontwit.barcodeapp.administration.order.processing.synchronization.Dictionary;

class DictionaryEntity {

    private Long id;
    private String value;

    Dictionary.Entry toDictionaryEntry() {
        return new Dictionary.Entry(id, value);
    }

}