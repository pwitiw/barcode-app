package com.frontwit.barcodeapp.administration.processing.model

import java.time.LocalDateTime

import static com.frontwit.barcodeapp.administration.processing.model.Stage.*

trait SampleFront {

    Barcode BARCODE = new Barcode(101L)
    LocalDateTime NOW = LocalDateTime.now()

    ProcessedFront aFrontWithCompletedProcessing() {
        return aFrontWithAppliedProcesses(1, MILLING, POLISHING, BASE, GRINDING, PAINTING, PACKING, IN_DELIVERY)
    }

    ProcessedFront aFrontWithAppliedProcess(ProcessingDetails details) {
        ProcessedFront front = aFront(BARCODE, 2)
        front.apply(details)
        return front
    }

    ProcessedFront aFrontWithAppliedProcesses(int quantity, Stage... stages) {
        ProcessedFront front = aFront(BARCODE, quantity)
        applyProcesses(front, stages)
        return front
    }

    ProcessedFront aFront(Barcode barcode, int quantity) {
        return new ProcessedFront(barcode, new Quantity(quantity), FrontProcessingPolicy.allPolicies())
    }

    LocalDateTime getIncrementedDateTime(seconds = 4) {
        NOW = NOW.plusSeconds(seconds)
    }

    private List<ProcessingDetails> applyProcesses(ProcessedFront front, Stage... stages) {
        stages
                .collect { stage -> new ProcessingDetails(stage, getIncrementedDateTime()) }
                .each { process -> front.apply process }
    }


}
