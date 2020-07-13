package com.frontwit.barcodeapp.administration.statistics.domain.order


import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod
import spock.lang.Specification

import java.time.Month
import java.time.Year

class WeeklyStatisticsCalculatorTest extends Specification {

    WeeklyStatisticsCalculator calculator = new WeeklyStatisticsCalculator()

    def "should successfully calculate orders meters weekly"() {
        given:
        def monday = new StatisticsPeriod(22, Month.JUNE as Month, Year.of(2020))
        def sunday = new StatisticsPeriod(21, Month.JUNE as Month, Year.of(2020))

        def statistics = [
                OrderStatistics.of(monday, Meters.of(1), Meters.of(2)),
                OrderStatistics.of(sunday, Meters.of(3), Meters.of(4)),
        ]
        when:
        def result = calculator.calculate(statistics, monday, OrderStatistics.&getOrders)

        then:
        assert result.value == 1100
    }
}
