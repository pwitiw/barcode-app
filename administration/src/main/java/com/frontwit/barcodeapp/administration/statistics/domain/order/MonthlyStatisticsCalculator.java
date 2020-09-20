package com.frontwit.barcodeapp.administration.statistics.domain.order;

import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;

import java.util.List;
import java.util.function.Function;

public class MonthlyStatisticsCalculator implements StatisticsCalculator {
    @Override
    public Meters calculate(List<OrderStatistics> orderStatistics,
                            StatisticsPeriod period,
                            Function<OrderStatistics, Meters> orderTypeMapper) {
        return orderStatistics.stream()
                .filter(o -> o.getPeriod().getMonth().equals(period.getMonth()))
                .map(orderTypeMapper)
                .reduce(Meters::plus)
                .orElse(Meters.ZERO);
    }
}

