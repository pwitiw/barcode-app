package com.frontwit.barcodeapp.administration.statistics.domain.order;

import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;

import java.util.List;
import java.util.function.Function;

public class YearlyStatisticsCalculator implements StatisticsCalculator {
    @Override
    public Meters calculate(List<OrderStatistics> orderStatistics,
                            StatisticsPeriod period,
                            Function<OrderStatistics, Meters> orderTypeMapper) {
        return orderStatistics.stream()
                .filter(statistics -> isSameYear(period, statistics))
                .map(orderTypeMapper)
                .reduce(Meters::plus).orElse(Meters.ZERO);
    }

    private boolean isSameYear(StatisticsPeriod period, OrderStatistics statistics) {
        return statistics.getPeriod().getYear().equals(period.getYear());
    }
}
