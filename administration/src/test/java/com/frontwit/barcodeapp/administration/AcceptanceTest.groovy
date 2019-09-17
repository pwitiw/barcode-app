package com.frontwit.barcodeapp.administration

import com.frontwit.barcodeapp.administration.order.processing.front.application.ProcessingFront
import com.frontwit.barcodeapp.administration.order.processing.front.application.dto.ProcessFrontCommand
import com.frontwit.barcodeapp.administration.order.processing.shared.Stage
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

import static com.frontwit.barcodeapp.administration.order.processing.shared.Stage.*

class AcceptanceTest extends IntegrationSpec {

    def ORDER_ID = 1L

    @Autowired
    ProcessingFront processingFacade

    def "order processing is completed"() {
        given: "test order exists in external"
        def barcode = 1001L
        when:
        frontIsBeingProcessed(barcode, status)

        then:
        def details = processingFacade.detailsFor(ORDER_ID)

        details != null
        details.getStage() == status
        details.getMissings().size() == 0

        where:
        status      | missings
        MILLING     | 0
        POLISHING   | 0
        BASE        | 0
        GRINDING    | 0
        PAINTING    | 0
        PACKING     | 0
        IN_DELIVERY | 0
    }

    def frontIsBeingProcessed(long barcode, Stage status) {
        processingFacade.processFront(new ProcessFrontCommand(barcode, status.getId(), LocalDateTime.now()))
    }
}
