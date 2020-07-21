package com.frontwit.barcodeapp.administration.statistics.domain.order


import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod
import spock.lang.Specification

import java.time.Month
import java.time.Year

class WeeklyStatisticsCalculatorTest extends Specification {

    WeeklyStatisticsCalculator calculator = new WeeklyStatisticsCalculator()

    def "should successfully calculate orders meters weekly for 1 statistics"() {
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

    def "should successfully calculate orders meters weekly for 2 statistics"() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(22, Month.JUNE as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(26, Month.JUNE as Month, Year.of(2020))

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

    def "should successfully calculate orders meters weekly for 3 statistics "() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(21, Month.JUNE as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(22, Month.JUNE as Month, Year.of(2020))
        def statisticsPeriod3 = new StatisticsPeriod(26, Month.JUNE as Month, Year.of(2020))
        def statisticsPeriod4 = new StatisticsPeriod(26, Month.JUNE as Month, Year.of(2020))

        def statistics = [
                OrderStatistics.of(statisticsPeriod1, Meters.of(1), Meters.of(2)),
                OrderStatistics.of(statisticsPeriod2, Meters.of(3), Meters.of(4)),
                OrderStatistics.of(statisticsPeriod3, Meters.of(5), Meters.of(6)),
                OrderStatistics.of(statisticsPeriod4, Meters.of(2), Meters.of(0)),
        ]
        when:
        def ordersResult = calculator.calculate(statistics, statisticsPeriod2, { it.getOrders() })
        def complaintsResult = calculator.calculate(statistics, statisticsPeriod2, { it.getComplaints() })

        then:
        assert ordersResult.value == 10.0
        assert complaintsResult.value == 10.0
    }
}