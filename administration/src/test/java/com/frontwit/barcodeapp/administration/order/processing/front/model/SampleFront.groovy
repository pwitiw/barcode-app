package com.frontwit.barcodeapp.administration.order.processing.front.model

import com.frontwit.barcodeapp.administration.order.processing.shared.Barcode
import com.frontwit.barcodeapp.administration.order.processing.shared.Quantity
import com.frontwit.barcodeapp.administration.order.processing.shared.Stage

import java.time.LocalDateTime

import static com.frontwit.barcodeapp.administration.order.processing.shared.Stage.*

trait SampleFront {

    Barcode BARCODE = new Barcode(101L)
    LocalDateTime NOW = LocalDateTime.now()

    Front aFrontWithCompletedProcessing() {
        aFrontWithAppliedProcesses(1, MILLING, POLISHING, BASE, GRINDING, PAINTING, PACKING, IN_DELIVERY)
    }

    Front aFrontWithAppliedProcess(ProcessingDetails details) {
        Front front = aFront(BARCODE, 2)
        front.apply(details)
        front
    }

    Front aFrontWithAppliedProcesses(int quantity, Stage... stages) {
        Front front = aFront(BARCODE, quantity)
        applyProcesses(front, stages)
        front
    }

    Front aFront(Barcode barcode, int quantity) {
        new Front(barcode, new Quantity(quantity), FrontProcessingPolicy.allPolicies())
    }

    LocalDateTime getIncrementedDateTime(seconds = 4) {
        NOW = NOW.plusSeconds(seconds)
    }

    private List<ProcessingDetails> applyProcesses(Front front, Stage... stages) {
        stages
                .collect { stage -> new ProcessingDetails(stage, getIncrementedDateTime()) }
                .each { process -> front.apply process }
    }


}
