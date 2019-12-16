package com.frontwit.barcodeapp.administration.order.processing.order.model

import com.frontwit.barcodeapp.administration.order.processing.shared.Barcode
import com.frontwit.barcodeapp.administration.order.processing.shared.OrderId
import com.frontwit.barcodeapp.administration.order.processing.shared.Stage
import spock.lang.Specification

import static com.frontwit.barcodeapp.administration.order.processing.shared.Stage.*

class OrderProcessingScenarios extends Specification {

    OrderId orderId = new OrderId(1L)
    Barcode barcode = Barcode.valueOf(orderId, 1)

    def "should update stage "() {
        given:
        def order = aOrderWithOneFront()
        when:
        order.update(aUpdateStageDetails(MILLING))
        then:
        order.getStage() == MILLING
        !order.isCompleted()
    }

    def "should change stage when at least one front processed"() {
        given:
        def order = aOrderWithTwoFronts()
        when:
        order.update(aUpdateStageDetails(MILLING))
        then:
        order.getStage() == MILLING
        !order.isCompleted()
    }

    def "should be completed when reach last stage"() {
        given:
        def order = aOrderWithoutLastProcess()
        when:
        order.update(aUpdateStageDetails(IN_DELIVERY))
        then:
        order.getStage() == IN_DELIVERY
        order.isCompleted()
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
        def fronts = frontsAtStage(INIT, barcode, Barcode.valueOf(orderId, 2))
        createOrder(orderId, fronts)
    }

    Order aOrderWithOneFront() {
        def fronts = frontsAtStage(INIT, barcode)
        createOrder(orderId, fronts)
    }

    Order aOrderWithoutLastProcess() {
        def fronts = frontsAtStage(PACKING, barcode)
        createOrder(orderId, fronts)
    }

    Order createOrder(OrderId orderId, Map<Barcode, Stage> fronts) {
        new Order(orderId, fronts, UpdateStagePolicy.allPolicies())
    }

    UpdateStageDetails aUpdateStageDetails(Stage stage) {
        new UpdateStageDetails(barcode, stage)
    }

    UpdateStageDetails aUnknownBarcodeUpdateStageDetails() {
        new UpdateStageDetails(new Barcode(201L), MILLING)
    }

    Map<Barcode, Stage> frontsAtStage(Stage stage, Barcode... barcodes) {
        Map<Barcode,Stage> fronts = barcodes.collectEntries {
            [(it): stage]
        }
        fronts
    }
}
