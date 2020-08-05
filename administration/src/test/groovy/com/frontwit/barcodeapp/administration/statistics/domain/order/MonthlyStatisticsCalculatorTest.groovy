package com.frontwit.barcodeapp.administration.statistics.domain.order

import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod
import spock.lang.Specification

import java.time.Month
import java.time.Year

class MonthlyStatisticsCalculatorTest extends Specification {

    MonthlyStatisticsCalculator calculator = new MonthlyStatisticsCalculator()

    def "should successfully calculate orders meters monthly for 3 statistics"() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(12, Month.JULY as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(13, Month.JULY as Month, Year.of(2020))
        def statisticsPeriod3 = new StatisticsPeriod(18, Month.JULY as Month, Year.of(2020))
        def statisticsPeriod4 = new StatisticsPeriod(12, Month.JUNE as Month, Year.of(2020))

        def statistics = [
                OrderStatistics.of(statisticsPeriod1, Meters.of(1), Meters.of(2)),
                OrderStatistics.of(statisticsPeriod2, Meters.of(3), Meters.of(4)),
                OrderStatistics.of(statisticsPeriod3, Meters.of(3), Meters.of(4)),
                OrderStatistics.of(statisticsPeriod4, Meters.of(3), Meters.of(4)),
        ]
        when:
        def ordersResult = calculator.calculate(statistics, statisticsPeriod1, { it.getOrders() })
        def complaintsResult = calculator.calculate(statistics, statisticsPeriod1, { it.getComplaints() })

        then:
        assert ordersResult.value == 7.0
        assert complaintsResult.value == 10.0
    }
    def "should successfully calculate orders meters monthly for 0 statistics"() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(12, Month.JULY as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(13, Month.JULY as Month, Year.of(2020))
        def statisticsPeriod3 = new StatisticsPeriod(18, Month.JULY as Month, Year.of(2020))
        def statisticsPeriod4 = new StatisticsPeriod(12, Month.JUNE as Month, Year.of(2020))

        def statistics = [
                OrderStatistics.of(statisticsPeriod1, Meters.of(1), Meters.of(2)),
                OrderStatistics.of(statisticsPeriod2, Meters.of(3), Meters.of(4)),
                OrderStatistics.of(statisticsPeriod3, Meters.of(3), Meters.of(4)),
        ]
        when:
        def ordersResult = calculator.calculate(statistics, statisticsPeriod4, { it.getOrders() })
        def complaintsResult = calculator.calculate(statistics, statisticsPeriod4, { it.getComplaints() })

        then:
        assert ordersResult.value == 0.0
        assert complaintsResult.value == 0.0
    }
}
