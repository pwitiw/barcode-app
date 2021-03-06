package com.frontwit.barcodeapp.administration.statistics

import com.frontwit.barcodeapp.administration.processing.order.model.OrderType
import com.frontwit.barcodeapp.administration.statistics.domain.OrderPlaced
import com.frontwit.barcodeapp.administration.statistics.domain.order.Meters
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatistics
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod

import java.time.Instant

class StatisticsFixtures {
    public static final Meters ONE_METER = Meters.of(1.0)
    public static final Instant TODAY = Instant.now()

    static OrderStatistics aStatistics() {
        return OrderStatistics.of(StatisticsPeriod.dailyFrom(Instant.now()))
    }

    static OrderPlaced anOrderPlacedEvent() {
        return new OrderPlaced(ONE_METER, Instant.now(), OrderType.ORDER)
    }
}
