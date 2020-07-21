package com.frontwit.barcodeapp.administration.statistics.domain.order;

import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;

import java.util.List;
import java.util.function.Function;

public class DailyStatisticsCalculator implements StatisticsCalculator {

    @Override
    public Meters calculate(List<OrderStatistics> orderStatistics,
                            StatisticsPeriod period,
                            Function<OrderStatistics, Meters> orderTypeMapper) {
        return orderStatistics.stream()
                .map(statistics -> {
                    if (statistics.isInPeriod(period)) {
                        return orderTypeMapper.apply(statistics);
                    }
                    return Meters.ZERO;
                })
                .reduce(Meters::plus)
                .orElse(Meters.ZERO);
    }
}