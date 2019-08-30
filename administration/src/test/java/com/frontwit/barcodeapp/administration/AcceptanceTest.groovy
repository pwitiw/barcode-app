package com.frontwit.barcodeapp.administration


import com.frontwit.barcodeapp.administration.processing.application.ProcessingFacade
import com.frontwit.barcodeapp.administration.processing.application.dto.ProcessFrontDto
import com.frontwit.barcodeapp.administration.processing.model.Stage
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
        details.getStage() == status
        details.getMissings().size() == 0

        where:
        status            | missings
        Stage.MILLING     | 0
        Stage.POLISHING   | 0
        Stage.BASE        | 0
        Stage.GRINDING    | 0
        Stage.PAINTING    | 0
        Stage.PACKING     | 0
        Stage.IN_DELIVERY | 0
    }

    def frontIsBeingProcessed(long barcode, Stage status) {
        processingFacade.processFront(new ProcessFrontDto(barcode, status.getId(), LocalDateTime.now()))
    }
}
