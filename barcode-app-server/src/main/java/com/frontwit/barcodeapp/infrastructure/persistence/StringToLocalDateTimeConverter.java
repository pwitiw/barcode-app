package com.frontwit.barcodeapp.infrastructure.persistence;

import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        return Instant
                .parse(source)
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
    }
}
