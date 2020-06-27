package com.frontwit.barcodeapp.administration

import com.frontwit.barcodeapp.administration.catalogue.OrderQuery
import com.frontwit.barcodeapp.administration.processing.front.application.FrontProcessor
import com.frontwit.barcodeapp.administration.processing.front.application.dto.ProcessFrontCommand
import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity
import com.frontwit.barcodeapp.administration.processing.shared.Stage
import com.frontwit.barcodeapp.administration.statistics.infrastructure.OrderStatisticsEntity
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

import static com.frontwit.barcodeapp.administration.processing.shared.Stage.*

class AcceptanceIT extends IntegrationSpec {

    @Autowired
    FrontProcessor processingFront

    @Autowired
    OrderQuery orderQuery

    def "order is synchronized when not present while processing"() {
        given:
        currentlyOrdersInDb(0)
        when:
        frontIsBeingProcessed(MILLING)
        then:
        currentlyOrdersInDb(1)
    }

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
        orderIsPacked()
    }

    def "statistics are added when order is synchronized"() {
      given:
        currentlyStatistics(0)
        when:
        frontIsBeingProcessed(MILLING)
        then:
        currentlyStatistics(1)
    }

    void orderIsUpdated(Stage stage) {
        def details = orderQuery.find(ORDER_ID)
        assert details.getStage() == stage
        assert details.getFronts().get(0).getAmendments().size() == 0
        assert details.getFronts().get(0).getProcessings().size() == stage.getId()
    }

    void orderIsPacked() {
        def details = orderQuery.find(ORDER_ID)
        assert details.getStage() == PACKING
        assert details.getFronts().get(0).getAmendments().size() == 0
        assert details.getFronts().get(0).getProcessings().size() == PACKING.getId()
        assert details.isPacked()
    }

    void frontIsBeingProcessed(Stage stage) {
        processingFront.process(new ProcessFrontCommand(BARCODE, stage.getId(), LocalDateTime.now()))
    }

    void currentlyOrdersInDb(int size) {
        assert mongoTemplate.findAll(OrderEntity.class).size() == size
    }

    void currentlyStatistics(int size) {
        assert mongoTemplate.findAll(OrderStatisticsEntity.class).size() == size
    }
}
