package com.frontwit.barcodeapp.administration.statistics.domain.order


import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod
import spock.lang.Specification

import java.time.Month
import java.time.Year

class WeeklyStatisticsCalculatorTest extends Specification {

    WeeklyStatisticsCalculator calculator = new WeeklyStatisticsCalculator()

    def "should successfully calculate weekly meters for 1 statistics"() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(0, 12, Month.JULY as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(0, 13, Month.JULY as Month, Year.of(2020))

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

    def "should successfully calculate weekly meters for 2 statistics"() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(0, 22, Month.JUNE as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(0, 26, Month.JUNE as Month, Year.of(2020))

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

    def "should successfully calculate weekly meters for 3 statistics "() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(0, 21, Month.JUNE as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(0, 22, Month.JUNE as Month, Year.of(2020))
        def statisticsPeriod3 = new StatisticsPeriod(0, 26, Month.JUNE as Month, Year.of(2020))
        def statisticsPeriod4 = new StatisticsPeriod(0, 26, Month.JUNE as Month, Year.of(2020))

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

    def "should successfully calculate weekly meters for 4 statistics "() {
        given:
        def statisticsPeriod1 = new StatisticsPeriod(0, 1, Month.AUGUST as Month, Year.of(2020))
        def statisticsPeriod2 = new StatisticsPeriod(0, 2, Month.AUGUST as Month, Year.of(2020))
        def statisticsPeriod3 = new StatisticsPeriod(0, 3, Month.AUGUST as Month, Year.of(2020))
        def statisticsPeriod4 = new StatisticsPeriod(0, 4, Month.AUGUST as Month, Year.of(2020))

        def statistics = [
                OrderStatistics.of(statisticsPeriod1, Meters.of(1), Meters.of(2)),
                OrderStatistics.of(statisticsPeriod2, Meters.of(1), Meters.of(2)),
                OrderStatistics.of(statisticsPeriod3, Meters.of(1), Meters.of(2)),
                OrderStatistics.of(statisticsPeriod4, Meters.of(1), Meters.of(2)),
        ]
        when:
        def ordersResult = calculator.calculate(statistics, statisticsPeriod2, { it.getOrders() })
        def complaintsResult = calculator.calculate(statistics, statisticsPeriod2, { it.getComplaints() })

        then:
        assert ordersResult.value == 2.0
        assert complaintsResult.value == 4.0
    }
}
