package com.frontwit.barcodeapp.administration.statistics.domain.order;

import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;

import java.util.List;
import java.util.Optional;

public interface OrderStatisticsRepository {
    void save(OrderStatistics orderStatistics);

    Optional<OrderStatistics> findBy(StatisticsPeriod statisticsPeriod);

    List<OrderStatistics> findForYearUntil(StatisticsPeriod statisticsPeriod);

    boolean empty();
}
