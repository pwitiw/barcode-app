package com.frontwit.barcodeapp.administration.statistics.domain.order;

import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;

import java.time.Month;
import java.util.List;
import java.util.function.Function;

public class QuarterlyStatisticsCalculator implements StatisticsCalculator {
    @Override
    public Meters calculate(List<OrderStatistics> orderStatistics,
                            StatisticsPeriod period,
                            Function<OrderStatistics, Meters> orderTypeMapper) {
        return orderStatistics.stream()
                .filter(o -> isInQuarter(period, o))
                .map(orderTypeMapper)
                .reduce(Meters::plus)
                .orElse(Meters.ZERO);
    }

    private boolean isInQuarter(StatisticsPeriod period,
                                OrderStatistics statistics) {
        var firstMonthOfQuarter = period.getMonth().firstMonthOfQuarter();
        var secondMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 1);
        var thirdMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        return statistics.isFrom(firstMonthOfQuarter)
                || statistics.isFrom(secondMonthOfQuarter)
                || statistics.isFrom(thirdMonthOfQuarter);
    }
}
