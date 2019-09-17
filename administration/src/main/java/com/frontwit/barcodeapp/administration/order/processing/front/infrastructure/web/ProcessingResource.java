package com.frontwit.barcodeapp.administration.order.processing.front.infrastructure.web;

import com.frontwit.barcodeapp.administration.order.processing.front.application.ProcessingFront;
import com.frontwit.barcodeapp.administration.order.processing.front.application.dto.ProcessFrontCommand;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ProcessingResource {

    ProcessingFront processingFront;

    @PostMapping("/apply")
    void process(@RequestBody ProcessFrontCommand command) {
        processingFront.process(command);
    }
}
