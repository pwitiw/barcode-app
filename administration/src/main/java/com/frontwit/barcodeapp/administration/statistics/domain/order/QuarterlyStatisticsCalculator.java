package com.frontwit.barcodeapp.administration.statistics.domain.order;

import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;

import java.time.Month;
import java.util.List;
import java.util.function.Function;

public class QuarterlyStatisticsCalculator implements StatisticsCalculator {
    @Override
    public Meters calculate(List<OrderStatistics> orderStatistics, StatisticsPeriod period, Function<OrderStatistics, Meters> orderTypeMapper) {
        var firstMonthOfQuarter = period.getMonth().firstMonthOfQuarter();
        var secondMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 1);
        var thirdMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        return orderStatistics.stream().map(statistics -> {
            if (statistics.isInMonth(firstMonthOfQuarter) || statistics.isInMonth(secondMonthOfQuarter) || statistics.isInMonth(thirdMonthOfQuarter)) {
                return orderTypeMapper.apply(statistics);
            }
            return Meters.ZERO;
        }).reduce(Meters::plus).orElse(Meters.ZERO);
    }
}
