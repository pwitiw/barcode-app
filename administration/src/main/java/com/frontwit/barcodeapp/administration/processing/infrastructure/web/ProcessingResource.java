package com.frontwit.barcodeapp.administration.processing.infrastructure.web;

import com.frontwit.barcodeapp.administration.processing.application.ProcessingFacade;
import com.frontwit.barcodeapp.administration.processing.application.dto.ProcessFrontDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ProcessingResource {

    ProcessingFacade processingFacade;

    @PostMapping("/apply")
    void process(@RequestBody ProcessFrontDto command) {
        processingFacade.processFront(command);
    }
}
