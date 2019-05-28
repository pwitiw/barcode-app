package com.frontwit.barcodeapp.domain.synchronization;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Dictionary {

    Map<Long, String> dictionary;

    Dictionary(List<Entry> entries) {
        this.dictionary = entries
                .stream()
                .collect(Collectors.toMap(Dictionary.Entry::getId, Dictionary.Entry::getValue));
    }

    String getValue(long id) {
        return dictionary.get(id);
    }

    @Getter
    class Entry {
        Long id;
        String value;
    }
}
