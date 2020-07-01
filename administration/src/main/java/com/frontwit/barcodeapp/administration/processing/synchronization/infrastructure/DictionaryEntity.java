package com.frontwit.barcodeapp.administration.processing.synchronization.infrastructure;

import com.frontwit.barcodeapp.administration.processing.synchronization.Dictionary;
import lombok.Data;

@Data
class DictionaryEntity {
    private Long id;
    private String value;

    Dictionary.Entry toDictionaryEntry() {
        return new Dictionary.Entry(id, value);
    }
}
