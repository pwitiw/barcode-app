package com.frontwit.barcodeapp.application.synchronization;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Dictionary {

    Map<Long, String> dictionary;

    private Dictionary(List<Entry> entries) {
        this.dictionary = entries
                .stream()
                .collect(Collectors.toMap(Dictionary.Entry::getId, Dictionary.Entry::getValue));
    }

    static Dictionary valueOf(List<Object[]> results) {
        List<Entry> entries = results
                .stream()
                .map(Dictionary.Entry::new)
                .collect(Collectors.toList());
        return new Dictionary(entries);
    }

    String getValue(long id) {
        return dictionary.get(Long.valueOf(id));
    }

    @Getter(value = AccessLevel.PRIVATE)
    static class Entry {
        Long id;
        String value;

        Entry(Object[] entry) {
            this.id = (long) (int) entry[0];
            this.value = (String) entry[1];
        }
    }
}
