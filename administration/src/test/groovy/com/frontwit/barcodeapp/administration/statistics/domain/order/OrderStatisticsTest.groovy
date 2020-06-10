package com.frontwit.barcodeapp.administration.statistics.domain.order

import com.frontwit.barcodeapp.administration.processing.order.model.OrderType
import spock.lang.Specification

import static com.frontwit.barcodeapp.administration.statistics.StatisticsFixtures.aStatistics

class OrderStatisticsTest extends Specification {
    private static final Meters ONE_METER = Meters.of(1.0)

    def "should add meters to order"() {
        given:
        def statistics = aStatistics()
        when:
        statistics.apply(OrderType.ORDER, ONE_METER)
        then:
        statistics.getOrders() == ONE_METER
    }

    def "should add meters to complainments"() {
        given:
        def statistics = aStatistics()
        when:
        statistics.apply(OrderType.COMPLAINT, ONE_METER)
        then:
        statistics.getComplainments() == ONE_METER
    }
}
