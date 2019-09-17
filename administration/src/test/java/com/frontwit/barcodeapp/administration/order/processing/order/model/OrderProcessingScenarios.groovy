package com.frontwit.barcodeapp.administration.order.processing.order.model

import com.frontwit.barcodeapp.administration.order.processing.shared.Barcode
import com.frontwit.barcodeapp.administration.order.processing.shared.OrderId
import com.frontwit.barcodeapp.administration.order.processing.shared.Stage
import spock.lang.Specification

class OrderProcessingScenarios extends Specification {

    OrderId orderId = new OrderId(1L)
    Barcode barcode = Barcode.valueOf(orderId, 1)

    def "should update stage "() {
        given:
        def order = aOrderWithOneFront()
        when:
        order.updateFrontStage(aMillingUpdateStageDetails())
        then:
        order.getStage() == Stage.MILLING
    }

    def "should not change stage when not all fronts processed"() {
        given:
        def order = aOrderWithTwoFronts()
        when:
        order.updateFrontStage(aMillingUpdateStageDetails())
        then:
        order.getStage() == Stage.INIT
    }

    def "should throw exception when order does not contain front"() {
        given:
        def order = aOrderWithOneFront()
        when:
        order.updateFrontStage(aUnknownBarcodeUpdateStageDetails())
        then:
        thrown(UpdateStageException)
    }

    Order aOrderWithTwoFronts() {
        def fronts = frontsAtInitStage(barcode, Barcode.valueOf(orderId, 2))
        createOrder(orderId, fronts)
    }

    Order aOrderWithOneFront() {
        def fronts = frontsAtInitStage(barcode)
        createOrder(orderId, fronts)
    }

    Order createOrder(OrderId orderId, Map<Barcode, Stage> fronts) {
        new Order(orderId, fronts, UpdateStagePolicy.allPolicies())
    }

    UpdateStageDetails aMillingUpdateStageDetails() {
        new UpdateStageDetails(barcode, Stage.MILLING)
    }

    UpdateStageDetails aUnknownBarcodeUpdateStageDetails() {
        new UpdateStageDetails(new Barcode(201L), Stage.MILLING)
    }

    Map<Barcode, Stage> frontsAtInitStage(Barcode... barcodes) {
        def fronts = barcodes.collectEntries {
            [(it): Stage.INIT]
        }
        fronts
    }
}
