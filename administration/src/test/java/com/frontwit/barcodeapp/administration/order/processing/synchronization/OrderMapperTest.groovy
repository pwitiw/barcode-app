package com.frontwit.barcodeapp.administration.order.processing.synchronization

import com.fasterxml.jackson.databind.ObjectMapper
import com.frontwit.barcodeapp.administration.order.processing.shared.Barcode
import com.frontwit.barcodeapp.administration.order.processing.shared.OrderId
import com.frontwit.barcodeapp.administration.order.processing.shared.Quantity
import spock.lang.Specification

class OrderMapperTest extends Specification implements SampleOrder {

    OrderMapper sut = new OrderMapper(new ObjectMapper())

    def "should synchronize order with fronts"() {
        when:
        TargetOrder result = sut.map(aSourceOrder(), aDictionary())
        then:
        result.getOrderId() == new OrderId(ID)
        result.getComment() == new TargetOrder.Comment(DESCRIPTION, ADDITIONAL_INFO)
        result.getInfo() == new TargetOrder.Info(COLOR, CUTTER, SIZE, NR)
        result.getFronts().size() == 1
        result.getFronts().get(0) ==
                new TargetFront(new Barcode(101), new Quantity(QUANTITY), new TargetFront.Dimensions(WIDTH, LENGTH), COMMENT)
    }
}
