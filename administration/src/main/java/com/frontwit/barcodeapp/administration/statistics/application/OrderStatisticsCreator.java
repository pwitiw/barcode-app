package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.statistics.domain.order.Meters;
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatistics;
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatisticsRepository;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.Month;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderStatisticsCreator {

    private final OrderStatisticsRepository orderStatisticsRepository;

    public OrderStatisticsDto statisticsFor(StatisticsPeriod today) {
        OrderStatisticsDto dto = new OrderStatisticsDto();
        var orderStatistics = orderStatisticsRepository.findForYearUntil(today);
        dto.setPeriods(
                Arrays.asList(
                        OrderStatisticsDto.PeriodDto.of(PeriodType.TODAY, aggregateMetersForToday(orderStatistics, today, OrderStatistics::getOrders), aggregateMetersForToday(orderStatistics, today, OrderStatistics::getComplaints)),
                        OrderStatisticsDto.PeriodDto.of(PeriodType.WEEK, aggregateMetersForWeek(orderStatistics, today, OrderStatistics::getOrders), aggregateMetersForWeek(orderStatistics, today, OrderStatistics::getComplaints)),
                        OrderStatisticsDto.PeriodDto.of(PeriodType.MONTH, aggregateMetersForMonth(orderStatistics, today, OrderStatistics::getOrders), aggregateMetersForMonth(orderStatistics, today, OrderStatistics::getComplaints)),
                        OrderStatisticsDto.PeriodDto.of(PeriodType.QUARTER, aggregateMetersForQuarter(orderStatistics, today, OrderStatistics::getOrders), aggregateMetersForQuarter(orderStatistics, today, OrderStatistics::getComplaints)),
                        OrderStatisticsDto.PeriodDto.of(PeriodType.YEAR, aggregateMetersForYear(orderStatistics, today, OrderStatistics::getOrders), aggregateMetersForYear(orderStatistics, today, OrderStatistics::getComplaints))
                )
        );
        return dto;
    }

    private Meters aggregateMetersForToday(List<OrderStatistics> orderStatistics, StatisticsPeriod period, Function<OrderStatistics, Meters> orderTypeMapper) {
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

    private Meters aggregateMetersForWeek(List<OrderStatistics> statistics, StatisticsPeriod date, Function<OrderStatistics, Meters> orderTypeMapper) {
        return filterStatistics(statistics, date).stream()
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

    private Meters aggregateMetersForMonth(List<OrderStatistics> orderStatistics, StatisticsPeriod today, Function<OrderStatistics, Meters> orderTypeMapper) {
        return orderStatistics.stream()
                .map(statistics -> {
                    if (statistics.getPeriod().getMonth().equals(today.getMonth())) {
                        return orderTypeMapper.apply(statistics);
                    }
                    return Meters.ZERO;
                })
                .reduce(Meters::plus)
                .orElse(Meters.ZERO);
    }

    private Meters aggregateMetersForQuarter(List<OrderStatistics> orderStatistics, StatisticsPeriod today, Function<OrderStatistics, Meters> orderTypeMapper) {
        var firstMonthOfQuarter = today.getMonth().firstMonthOfQuarter();
        var secondMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 1);
        var thirdMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        return orderStatistics.stream().map(statistics -> {
            if (statistics.isInMonth(firstMonthOfQuarter) || statistics.isInMonth(secondMonthOfQuarter) || statistics.isInMonth(thirdMonthOfQuarter)) {
                return orderTypeMapper.apply(statistics);
            }
            return Meters.ZERO;
        }).reduce(Meters::plus).orElse(Meters.ZERO);
    }

    private Meters aggregateMetersForYear(List<OrderStatistics> orderStatistics, StatisticsPeriod today, Function<OrderStatistics, Meters> orderTypeMapper) {
        return orderStatistics.stream().map(statistics -> {
            if (statistics.getPeriod().getYear().equals(today.getYear())) {
                return orderTypeMapper.apply(statistics);
            }
            return Meters.ZERO;
        }).reduce(Meters::plus).orElse(Meters.ZERO);
    }
}
