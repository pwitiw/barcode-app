package com.frontwit.barcodeapp.administration.statistics.domain.order

import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod
import spock.lang.Specification

import java.time.Month
import java.time.Year

class DailyStatisticsCalculatorTest extends Specification {

    DailyStatisticsCalculator calculator = new DailyStatisticsCalculator()

    def "should successfully calculate orders meters daily for 2 statistics"() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(12, Month.JULY as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(12, Month.JULY as Month, Year.of(2020))

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

    def "should successfully calculate orders meters daily for 1 statistics"() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(12, Month.JULY as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(13, Month.JULY as Month, Year.of(2020))

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

    def "should successfully calculate orders meters daily for 0 statistics"() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(12, Month.JULY as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(13, Month.JULY as Month, Year.of(2020))
        def statisticsPeriod3 = new StatisticsPeriod(14, Month.JULY as Month, Year.of(2020))

        def statistics = [
                OrderStatistics.of(statisticsPeriod1, Meters.of(1), Meters.of(2)),
                OrderStatistics.of(statisticsPeriod2, Meters.of(3), Meters.of(4)),
        ]
        when:
        def ordersResult = calculator.calculate(statistics, statisticsPeriod3, { it.getOrders() })
        def complaintsResult = calculator.calculate(statistics, statisticsPeriod3, { it.getComplaints() })

        then:
        assert ordersResult.value == 0.0
        assert complaintsResult.value == 0.0
    }
}
