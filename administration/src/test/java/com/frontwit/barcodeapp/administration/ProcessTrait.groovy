package com.frontwit.barcodeapp.administration

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.frontwit.barcodeapp.administration.application.order.Stage
import com.frontwit.barcodeapp.administration.application.order.dto.ProcessCommand

import java.time.LocalDateTime

trait ProcessTrait {

    String aJsonProcessCommand(Long barcode, Stage stage, ObjectMapper mapper) throws JsonProcessingException {
        ProcessCommand processCommand = new ProcessCommand(barcode, stage.getId(), LocalDateTime.now());
        return "[" + mapper.writeValueAsString(processCommand) + "]"
    }
}