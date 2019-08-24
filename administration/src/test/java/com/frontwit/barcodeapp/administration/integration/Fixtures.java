package com.frontwit.barcodeapp.administration.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.administration.application.order.BarcodeConverter;
import com.frontwit.barcodeapp.administration.application.order.dto.ProcessCommand;

import java.time.LocalDateTime;

public class Fixtures {

    public static String aProcessCommandJson(Long id, ObjectMapper mapper) throws JsonProcessingException {
        ProcessCommand processCommand = new ProcessCommand(BarcodeConverter.toBarcode(id), 1, LocalDateTime.now());
        return mapper.writeValueAsString(processCommand);
    }
}
