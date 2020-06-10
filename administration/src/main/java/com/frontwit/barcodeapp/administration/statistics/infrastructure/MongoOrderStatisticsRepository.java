package com.frontwit.barcodeapp.administration.statistics.infrastructure;

import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatistics;
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatisticsRepository;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;

import java.util.Optional;

public class MongoOrderStatisticsRepository implements OrderStatisticsRepository {

    @Override
    public void save(OrderStatistics orderStatistics) {

    }

    @Override
    public Optional<OrderStatistics> findBy(StatisticsPeriod statisticsPeriod) {
        return Optional.empty();
    }
}
