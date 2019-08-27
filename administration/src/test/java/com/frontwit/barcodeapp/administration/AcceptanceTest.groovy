package com.frontwit.barcodeapp.administration

import com.frontwit.barcodeapp.administration.application.order.dto.ProcessCommand
import com.frontwit.barcodeapp.administration.processing.application.ProcessingFacade
import com.frontwit.barcodeapp.administration.processing.domain.Status
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

class AcceptanceTest extends IntegrationSpec {

    def ORDER_ID = 1L

    @Autowired
    ProcessingFacade processingFacade

    def "order processing is completed"() {
        given: "test order exists in external"
        def barcode = 1001L
        when:
        frontIsBeingProcessed(barcode, status)

        then:
        def details = processingFacade.detailsFor(ORDER_ID)

        details != null
        details.getStatus() == status
        details.getMissings().size() == 0

        where:
        status             | missings
        Status.MILLING     | 0
        Status.POLISHING   | 0
        Status.BASE        | 0
        Status.GRINDING    | 0
        Status.PAINTING    | 0
        Status.PACKING     | 0
        Status.IN_DELIVERY | 0
    }

    def frontIsBeingProcessed(long barcode, Status status) {
        processingFacade.processFront(new ProcessCommand(barcode, status.id, LocalDateTime.now()))
    }
}
