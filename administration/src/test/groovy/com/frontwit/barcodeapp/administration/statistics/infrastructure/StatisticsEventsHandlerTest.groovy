package com.frontwit.barcodeapp.administration.statistics.infrastructure


import com.frontwit.barcodeapp.administration.processing.front.model.FrontStatisticsRepository
import com.frontwit.barcodeapp.administration.processing.order.model.OrderType
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatistics
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatisticsRepository
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod
import com.frontwit.barcodeapp.administration.statistics.domain.stage.StageStatisticsRepository
import spock.lang.Specification

import static com.frontwit.barcodeapp.administration.statistics.StatisticsFixtures.*

class StatisticsEventsHandlerTest extends Specification {

    private OrderStatisticsRepository orderStatisticsRepository = Mock()
    private StageStatisticsRepository stageStatisticsRepository = Mock()
    private FrontStatisticsRepository frontStatisticsRepository = Mock()
    private StatisticsEventsHandler statisticsEventsHandler = new StatisticsEventsHandler(orderStatisticsRepository, stageStatisticsRepository, frontStatisticsRepository)

    def "should create statistics for today if not present"() {
        given:
        thereAreNoStatisticsForToday()
        and:

        when:
        statisticsEventsHandler.handle(anOrderPlacedEvent())
        then:
        orderStatisticsRepository.save(_ as OrderStatistics) >> {
            it.period == StatisticsPeriod.dailyFrom(TODAY)
        }
    }

    def "should update statistics for today when new order is placed"() {
        given:
        def statistics = thereAreSomeStatisticsWithOneMeterForToday()
        when:
        statisticsEventsHandler.handle(anOrderPlacedEvent())
        then:
        1 * orderStatisticsRepository.save(statistics)
    }

    void thereAreNoStatisticsForToday() {
        orderStatisticsRepository.findBy(_ as StatisticsPeriod) >> Optional.empty()
    }

    OrderStatistics thereAreSomeStatisticsWithOneMeterForToday() {
        def period = StatisticsPeriod.dailyFrom(TODAY)
        def statistics = OrderStatistics.of(period)
        statistics.apply(OrderType.ORDER, ONE_METER)
        orderStatisticsRepository.findBy(period) >> Optional.of(statistics)
        statistics
    }
}
