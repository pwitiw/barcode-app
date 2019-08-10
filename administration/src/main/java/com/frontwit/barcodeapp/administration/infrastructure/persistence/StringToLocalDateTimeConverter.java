package com.frontwit.barcodeapp.administration.infrastructure.persistence;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ReadingConverter
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        return Instant
                .parse(source)
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
    }
}
