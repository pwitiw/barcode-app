package com.frontwit.barcodeapp.administration.statistics.application

import com.frontwit.barcodeapp.administration.statistics.domain.order.Meters
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatistics
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatisticsRepository
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod
import spock.lang.Specification

import java.time.Month
import java.time.Year

import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.expect

class OrderStatisticsCreatorTest extends Specification {
    OrderStatisticsRepository repository = Mock()
    OrderStatisticsCreator orderStatisticsCreator = new OrderStatisticsCreator(repository)

    def "should create statistics for today"() {
        given:
        def thursday = new StatisticsPeriod(19, Month.JUNE as Month, Year.of(2020))
        def monday = new StatisticsPeriod(16, Month.JUNE as Month, Year.of(2020))
        def wednesday = new StatisticsPeriod(10, Month.JUNE as Month, Year.of(2020))
        def friday = new StatisticsPeriod(2, Month.MAY as Month, Year.of(2020))
        def tuesday = new StatisticsPeriod(7, Month.FEBRUARY as Month, Year.of(2020))

        def statistics = [
                OrderStatistics.of(thursday, Meters.of(1), Meters.of(1)),
                OrderStatistics.of(monday, Meters.of(1), Meters.of(1)),
                OrderStatistics.of(wednesday, Meters.of(1), Meters.of(1)),
                OrderStatistics.of(friday, Meters.of(1), Meters.of(1)),
                OrderStatistics.of(tuesday, Meters.of(1), Meters.of(1)),
        ]
        repository.findForYearUntil(thursday) >> statistics
        when:
        def result = orderStatisticsCreator.statisticsFor(thursday)

        then:
        expect result.getPeriods(), containsInAnyOrder(
                periodDto(PeriodType.TODAY, 1, 1),
                periodDto(PeriodType.WEEK, 2, 2),
                periodDto(PeriodType.MONTH, 3, 3),
                periodDto(PeriodType.QUARTER, 4, 4),
                periodDto(PeriodType.YEAR, 5, 5)
        )
    }

    private static OrderStatisticsDto.PeriodDto periodDto(PeriodType type, Double orders, Double complaints) {
        new OrderStatisticsDto.PeriodDto(type, orders, complaints)
    }
}
