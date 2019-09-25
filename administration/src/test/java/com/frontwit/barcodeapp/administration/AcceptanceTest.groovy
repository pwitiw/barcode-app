package com.frontwit.barcodeapp.administration

import com.frontwit.barcodeapp.administration.order.processing.front.application.ProcessingFront
import com.frontwit.barcodeapp.administration.order.processing.front.application.dto.ProcessFrontCommand
import com.frontwit.barcodeapp.administration.order.processing.order.infrastructure.OrderQuery
import com.frontwit.barcodeapp.administration.order.processing.shared.Stage
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

import static com.frontwit.barcodeapp.administration.order.processing.shared.Stage.*

class AcceptanceTest extends IntegrationSpec {

    @Autowired
    ProcessingFront processingFront

    @Autowired
    OrderQuery orderQuery

    def "order processing is completed"() {
        given: "test data is already prepared"
        when:
        frontIsBeingProcessed(MILLING)
        then:
        orderIsUpdated(MILLING)
        when:
        frontIsBeingProcessed(POLISHING)
        then:
        orderIsUpdated(POLISHING)
        when:
        frontIsBeingProcessed(BASE)
        then:
        orderIsUpdated(BASE)
        when:
        frontIsBeingProcessed(GRINDING)
        then:
        orderIsUpdated(GRINDING)
        when:
        frontIsBeingProcessed(PAINTING)
        then:
        orderIsUpdated(PAINTING)
        when:
        frontIsBeingProcessed(PACKING)
        then:
        orderIsUpdated(PACKING)
        when:
        frontIsBeingProcessed(IN_DELIVERY)
        then:
        orderIsUpdated(IN_DELIVERY)
    }

    void orderIsUpdated(Stage stage) {
        def details = orderQuery.find(ORDER_ID)
        assert details.getStage() == stage
        assert details.getFronts().get(0).getAmendments().size() == 0
        assert details.getFronts().get(0).getProcessings().size() == stage.getId()
    }

    void frontIsBeingProcessed(Stage status) {
        processingFront.process(new ProcessFrontCommand(BARCODE.getBarcode(), status.getId(), LocalDateTime.now()))
    }
}
