package com.frontwit.barcodeapp.administration.statistics.application

import com.frontwit.barcodeapp.administration.processing.order.model.OrderType
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatistics
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatisticsRepository
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod
import spock.lang.Specification

import static com.frontwit.barcodeapp.administration.statistics.StatisticsFixtures.*

class StatisticsEventsHandlerTest extends Specification {

    private OrderStatisticsRepository repository = Mock()
    private StageStatisticsRepository frontPackedRepository = Mock()
    private StatisticsEventsHandler statisticsEventsHandler = new StatisticsEventsHandler(repository, frontPackedRepository)

    def "should create statistics for today if not present"() {
        given:
        thereAreNoStatisticsForToday()
        and:

        when:
        statisticsEventsHandler.handle(anOrderPlacedEvent())
        then:
        repository.save(_ as OrderStatistics) >> {
            it.period == StatisticsPeriod.of(TODAY)
        }
    }

    def "should update statistics for today when new order is placed"() {
        given:
        def statistics = thereAreSomeStatisticsWithOneMeterForToday()
        when:
        statisticsEventsHandler.handle(anOrderPlacedEvent())
        then:
        1 * repository.save(statistics)
    }

    void thereAreNoStatisticsForToday() {
        repository.findBy(_ as StatisticsPeriod) >> Optional.empty()
    }

    OrderStatistics thereAreSomeStatisticsWithOneMeterForToday() {
        def period = StatisticsPeriod.of(TODAY)
        def statistics = OrderStatistics.of(period)
        statistics.apply(OrderType.ORDER, ONE_METER)
        repository.findBy(period) >> Optional.of(statistics)
        statistics
    }
}
