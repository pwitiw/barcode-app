package com.frontwit.barcodeapp.administration.statistics.domain.order;

import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;

import java.sql.Date;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WeeklyStatisticsCalculator implements StatisticsCalculator {
    @Override
    public Meters calculate(List<OrderStatistics> orderStatistics, StatisticsPeriod period, Function<OrderStatistics, Meters> orderTypeMapper) {
        return filterStatistics(orderStatistics, period).stream()
                .map(orderTypeMapper)
                .reduce(Meters::plus)
                .orElse(Meters.ZERO);
    }

    private List<OrderStatistics> filterStatistics(List<OrderStatistics> statistics, StatisticsPeriod date) {
        var monday = findMonday(date);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK, 6);
        var sunday = StatisticsPeriod.of(c.toInstant());
        return filterStatisticsBetween(statistics, monday, sunday);
    }

    private List<OrderStatistics> filterStatisticsBetween(List<OrderStatistics> orderStatistics, StatisticsPeriod from, StatisticsPeriod to) {
        return orderStatistics.stream()
                .filter(s -> s.getPeriod().toInstant().isAfter(from.toInstant()) && s.getPeriod().toInstant().isBefore(to.toInstant()))
                .collect(Collectors.toList());
    }

    private StatisticsPeriod findMonday(StatisticsPeriod date) {
        StatisticsPeriod foundPeriod = null;
        Calendar c = Calendar.getInstance();
        c.setTime(Date.from(date.toInstant()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        for (var i = 0; i < 8; i++) {
            if (dayOfWeek - i == 1)
                c.add(Calendar.DAY_OF_WEEK, (-i));
            Instant foundDate = c.toInstant();
            foundPeriod = StatisticsPeriod.of(foundDate);
        }
        return foundPeriod;
    }
}

