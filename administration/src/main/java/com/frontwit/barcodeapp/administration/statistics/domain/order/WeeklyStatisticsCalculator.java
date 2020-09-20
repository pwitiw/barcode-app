package com.frontwit.barcodeapp.administration.statistics.domain.order;

import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.DayOfWeek.MONDAY;

public class WeeklyStatisticsCalculator implements StatisticsCalculator {
    @Override
    public Meters calculate(List<OrderStatistics> orderStatistics,
                            StatisticsPeriod period,
                            Function<OrderStatistics, Meters> orderTypeMapper) {
        return getStatisticsBetween(orderStatistics, period).stream()
                .map(orderTypeMapper)
                .reduce(Meters::plus)
                .orElse(Meters.ZERO);
    }

    private List<OrderStatistics> getStatisticsBetween(List<OrderStatistics> statistics, StatisticsPeriod date) {
        var monday = getFirstDayOfWeek(date);
        var sunday = StatisticsPeriod.of(monday.toInstant().plus(6, ChronoUnit.DAYS));
        return filterStatisticsBetween(statistics, monday, sunday);
    }

    private List<OrderStatistics> filterStatisticsBetween(List<OrderStatistics> orderStatistics,
                                                          StatisticsPeriod from,
                                                          StatisticsPeriod to) {
        return orderStatistics.stream()
                .filter(statistics -> isAfter(from, statistics) && isBefore(to, statistics))
                .collect(Collectors.toList());
    }

    private boolean isAfter(StatisticsPeriod from, OrderStatistics s) {
        return s.getPeriod().toInstant().isAfter(from.toInstant().minus(1, ChronoUnit.DAYS));
    }

    private boolean isBefore(StatisticsPeriod to, OrderStatistics s) {
        return s.getPeriod().toInstant().isBefore(to.toInstant().plus(1, ChronoUnit.DAYS));
    }

    private StatisticsPeriod getFirstDayOfWeek(StatisticsPeriod date) {
        var dayOfWeek = date.toInstant().atZone(ZoneId.systemDefault()).getDayOfWeek().getValue();
        var firstDayOfWeek = date.toInstant().minus(dayOfWeek - MONDAY.getValue(), ChronoUnit.DAYS);
        return StatisticsPeriod.of(firstDayOfWeek);
    }
}

