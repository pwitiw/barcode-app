package com.frontwit.barcodeapp.administration.processing.order.model

import com.frontwit.barcodeapp.api.shared.Barcode
import com.frontwit.barcodeapp.api.shared.OrderId
import com.frontwit.barcodeapp.api.shared.Stage
import spock.lang.Specification

import static com.frontwit.barcodeapp.administration.processing.order.model.UpdateStagePolicy.*
import static com.frontwit.barcodeapp.api.shared.Stage.*

class OrderProcessingScenarios extends Specification {

    OrderId orderId = new OrderId(1L)
    Barcode barcode = Barcode.valueOf(orderId, 1)

    def "should update stage and processing date"() {
        given:
        def order = aOrderWithOneFront()
        when:
        order.update(aUpdateStageDetails(MILLING))
        then:
        order.getStage() == MILLING
        order.getLastProcessedOn() != null
    }

    def "should change stage when at least one front processed"() {
        given:
        def order = aOrderWithTwoFronts()
        when:
        order.update(aUpdateStageDetails(MILLING))
        then:
        order.getStage() == MILLING
    }

    def "is packed when all fronts are packed"() {
        given:
        def order = aOrderWithOneFront()
        when:
        order.pack(new PackFront(barcode))
        then:
        order.isPacked()
    }


    def "is not packed when not all fronts are packed"() {
        given:
        def order = aOrderWithTwoFronts()
        when:
        order.pack(new PackFront(barcode))
        then:
        !order.isPacked()
    }


    def "should throw exception when order does not contain front"() {
        given:
        def order = aOrderWithOneFront()
        when:
        order.update(aUnknownBarcodeUpdateStageDetails())
        then:
        thrown(UpdateStageException)
    }

    Order aOrderWithTwoFronts() {
        def fronts = [barcode, Barcode.valueOf(orderId, 2)].toSet()
        createOrder(orderId, fronts)
    }

    Order aOrderWithOneFront() {
        def fronts = [barcode].toSet()
        createOrder(orderId, fronts)
    }

    static Order createOrder(OrderId orderId, Set<Barcode> fronts) {
        new Order(orderId, allPolicies(), fronts)
    }

    UpdateStageDetails aUpdateStageDetails(Stage stage) {
        new UpdateStageDetails(barcode, stage)
    }

    static UpdateStageDetails aUnknownBarcodeUpdateStageDetails() {
        new UpdateStageDetails(new Barcode(201L), MILLING)
    }
}
