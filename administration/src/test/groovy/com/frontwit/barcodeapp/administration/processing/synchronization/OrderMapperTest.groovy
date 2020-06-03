package com.frontwit.barcodeapp.administration.processing.synchronization

import com.fasterxml.jackson.databind.ObjectMapper
import com.frontwit.barcodeapp.administration.processing.order.model.OrderType
import com.frontwit.barcodeapp.administration.processing.shared.Barcode
import com.frontwit.barcodeapp.administration.processing.shared.OrderId
import com.frontwit.barcodeapp.administration.processing.shared.Quantity
import spock.lang.Specification

class OrderMapperTest extends Specification implements SampleOrder {

    OrderMapper sut = new OrderMapper(new ObjectMapper())

    def "should synchronize order with fronts"() {
        when:
        TargetOrder result = sut.map(aSourceOrder(), aDictionary())
        then:
        result.getOrderId() == new OrderId(ID)
        result.getComment() == new TargetOrder.Comment(DESCRIPTION, ADDITIONAL_INFO)
        sss(result)
        result.getFronts().size() == 1
        result.getFronts().get(0) ==
                new TargetFront(Barcode.valueOf(new OrderId(ID), 1), new OrderId(ID), new Quantity(QUANTITY), new TargetFront.Dimensions(WIDTH, LENGTH), COMMENT)
    }

    private void sss(result) {
        assert result.getInfo() == new TargetOrder.Info(COLOR, CUTTER, SIZE, NR, ORDERED_AT, OrderType.ORDER)
    }
}
