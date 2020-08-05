package com.frontwit.barcodeapp.administration.statistics.domain.order

import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod
import spock.lang.Specification

import java.time.Month
import java.time.Year

class QuarterlyStatisticsCalculatorTest extends Specification {

    QuarterlyStatisticsCalculator calculator = new QuarterlyStatisticsCalculator()

    def "should successfully calculate orders meters quarterly for 2 statistics"() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(12, Month.JANUARY as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(12, Month.FEBRUARY as Month, Year.of(2020))

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

    def "should successfully calculate orders meters quarterly for 1 statistics"() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(12, Month.MARCH as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(13, Month.APRIL as Month, Year.of(2020))

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

    def "should successfully calculate orders meters quarterly for 3 statistics"() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(12, Month.SEPTEMBER as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(13, Month.JULY as Month, Year.of(2020))
        def statisticsPeriod3 = new StatisticsPeriod(14, Month.AUGUST as Month, Year.of(2020))

        def statistics = [
                OrderStatistics.of(statisticsPeriod1, Meters.of(1), Meters.of(2)),
                OrderStatistics.of(statisticsPeriod2, Meters.of(3), Meters.of(4)),
                OrderStatistics.of(statisticsPeriod3, Meters.of(2), Meters.of(0)),
        ]
        when:
        def ordersResult = calculator.calculate(statistics, statisticsPeriod1, { it.getOrders() })
        def complaintsResult = calculator.calculate(statistics, statisticsPeriod1, { it.getComplaints() })

        then:
        assert ordersResult.value == 6.0
        assert complaintsResult.value == 6.0
    }
}
