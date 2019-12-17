package com.frontwit.barcodeapp.administration.processing.synchronization;

import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class Dictionary {

    private final Map<Long, String> entries;

    public Dictionary(List<Entry> entries) {
        this.entries = entries.stream().collect(Collectors.toMap(Entry::getId, Entry::getValue));
    }

    String getValue(long color) {
        return Optional.of(entries.get(color)).orElse("");
    }

    @Value
    public static class Entry {
        Long id;
        String value;
    }
}
