package com.frontwit.barcodeapp.administration.statistics.domain.order

import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod
import spock.lang.Specification

import java.time.Month
import java.time.Year

class YearlyStatisticsCalculatorTest extends Specification {

    YearlyStatisticsCalculator calculator = new YearlyStatisticsCalculator()

    def "should successfully calculate orders meters yearly for 2 statistics"() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(12, Month.AUGUST as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(12, Month.DECEMBER as Month, Year.of(2020))

        def statistics = [
                OrderStatistics.of(statisticsPeriod1, Meters.of(1), Meters.of(2)),
                OrderStatistics.of(statisticsPeriod2, Meters.of(3), Meters.of(4)),
        ]
        when:
        def ordersResult = calculator.calculate(statistics, statisticsPeriod1, { it.getOrders() })
        def complaintsResult = calculator.calculate(statistics, statisticsPeriod1, { it.getComplaints() })

        then:
        assert ordersResult.value == 4.0
        assert complaintsResult.value == 6.0
    }

    def "should successfully calculate orders meters yearly for 1 statistics"() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(12, Month.AUGUST as Month, Year.of(2019))
        def statisticsPeriod2 = new StatisticsPeriod(12, Month.DECEMBER as Month, Year.of(2020))

        def statistics = [
                OrderStatistics.of(statisticsPeriod1, Meters.of(1), Meters.of(2)),
                OrderStatistics.of(statisticsPeriod2, Meters.of(3), Meters.of(4)),
        ]
        when:
        def ordersResult = calculator.calculate(statistics, statisticsPeriod1, { it.getOrders() })
        def complaintsResult = calculator.calculate(statistics, statisticsPeriod1, { it.getComplaints() })

        then:
        assert ordersResult.value == 1.0
        assert complaintsResult.value == 2.0
    }
}
