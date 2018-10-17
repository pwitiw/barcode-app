package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dto.ComponentDto;
import com.frontwit.barcodeapp.dto.OrderDetailDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;

public final class CsvService {

    private final CSVFormat format;
    private static final char SEPARATOR = ';';

    public CsvService() {
        format = CSVFormat.newFormat(SEPARATOR)
                .withHeader(CsvField.class)
                .withSkipHeaderRecord();
    }

    public OrderDetailDto parse(final MultipartFile multipartFile) {
        final OrderDetailDto orderDetail = new OrderDetailDto();
        try {
            InputStreamReader in = new InputStreamReader(multipartFile.getInputStream());
            CSVParser parser = format.parse(in);
            parser.getRecords().forEach(record -> {
                orderDetail.components.add(toComponent(record));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderDetail;
    }

    private ComponentDto toComponent(CSVRecord record) {
        ComponentDto component = new ComponentDto();
        // TODO dokonczyc

        return component;
    }

    enum CsvField {
        NR_ZAM,
        ILOSC,
        WYSOKOSC,
        SZEROKOSC
    }

}
