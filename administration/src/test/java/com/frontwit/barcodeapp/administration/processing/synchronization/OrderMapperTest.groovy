package com.frontwit.barcodeapp.administration.processing.synchronization

import com.fasterxml.jackson.databind.ObjectMapper
import com.frontwit.barcodeapp.administration.processing.shared.Barcode
import com.frontwit.barcodeapp.administration.processing.shared.OrderId
import com.frontwit.barcodeapp.administration.processing.shared.Quantity
import spock.lang.Specification

import java.time.LocalDate

class OrderMapperTest extends Specification implements SampleOrder {

    OrderMapper sut = new OrderMapper(new ObjectMapper())

    def "should synchronize order with fronts"() {
        when:
        TargetOrder result = sut.map(aSourceOrder(), aDictionary())
        then:
        result.getOrderId() == new OrderId(ID)
        result.getComment() == new TargetOrder.Comment(DESCRIPTION, ADDITIONAL_INFO)
        result.getInfo() == new TargetOrder.Info(COLOR, CUTTER, SIZE, NR, CUSTOMER, LocalDate.parse(ORDERED_AT))
        result.getFronts().size() == 1
        result.getFronts().get(0) ==
                new TargetFront(Barcode.valueOf(new OrderId(ID),1), new OrderId(ID), new Quantity(QUANTITY), new TargetFront.Dimensions(WIDTH, LENGTH), COMMENT)
    }
}
