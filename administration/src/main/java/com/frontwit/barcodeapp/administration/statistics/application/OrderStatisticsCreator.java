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
                        OrderStatisticsDto.PeriodDto.of(PeriodType.TODAY, calculateOrdersMetersToday(orderStatistics, today), calculateComplaintsMetersToday(orderStatistics, today)),
                        OrderStatisticsDto.PeriodDto.of(PeriodType.WEEK, calculateOrdersWeekly(orderStatistics, today), calculateComplaintsWeekly(orderStatistics, today)),
                        OrderStatisticsDto.PeriodDto.of(PeriodType.MONTH, calculateOrdersMonthly(orderStatistics, today), calculateComplaintsMonthly(orderStatistics, today)),
                        OrderStatisticsDto.PeriodDto.of(PeriodType.QUARTER, calculateOrdersQuarter(orderStatistics, today), calculateComplaintsQuarter(orderStatistics, today)),
                        OrderStatisticsDto.PeriodDto.of(PeriodType.YEAR, calculateOrdersYear(orderStatistics, today), calculateComplaintsYear(orderStatistics, today))
                )
        );
        return dto;
    }

    private Meters calculateOrdersMetersToday(List<OrderStatistics> orderStatistics, StatisticsPeriod period) {
        return orderStatistics.stream().map(statistics -> {
            if (statistics.isInPeriod(period)) {
                return statistics.getOrders();
            }
            return Meters.ZERO;
        }).reduce(Meters::plus)
                .orElse(Meters.ZERO);
    }

    private Meters calculateComplaintsMetersToday(List<OrderStatistics> orderStatistics, StatisticsPeriod period) {
        return orderStatistics.stream().map(statistics -> {
            if (statistics.isInPeriod(period)) {
                return statistics.getComplaints();
            }
            return Meters.ZERO;
        }).reduce(Meters::plus).orElse(Meters.ZERO);
    }

    private Meters calculateOrdersWeekly(List<OrderStatistics> statistics, StatisticsPeriod date) {
        return filterStatistics(statistics, date).stream()
                .map(OrderStatistics::getOrders)
                .reduce(Meters::plus)
                .orElse(Meters.ZERO);
    }

    private Meters calculateComplaintsWeekly(List<OrderStatistics> statistics, StatisticsPeriod date) {
        return filterStatistics(statistics, date).stream()
                .map(OrderStatistics::getComplaints)
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

    private Meters calculateComplaintsMonthly(List<OrderStatistics> orderStatistics, StatisticsPeriod today) {
        return orderStatistics.stream().map(statistics -> {
            if (statistics.getPeriod().getMonth().equals(today.getMonth())) {
                return statistics.getComplaints();
            }
            return Meters.ZERO;
        }).reduce(Meters::plus).orElse(Meters.ZERO);
    }

    private Meters calculateOrdersMonthly(List<OrderStatistics> orderStatistics, StatisticsPeriod today) {
        return orderStatistics.stream().map(statistics -> {
            if (statistics.getPeriod().getMonth().equals(today.getMonth())) {
                return statistics.getOrders();
            }
            return Meters.ZERO;
        }).reduce(Meters::plus).orElse(Meters.ZERO);
    }

    private Meters calculateComplaintsQuarter(List<OrderStatistics> orderStatistics, StatisticsPeriod today) {
        var firstMonthOfQuarter = today.getMonth().firstMonthOfQuarter();
        var secondMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 1);
        var thirdMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        return orderStatistics.stream().map(statistics -> {
            if (statistics.isInMonth(firstMonthOfQuarter) || statistics.isInMonth(secondMonthOfQuarter) || statistics.isInMonth(thirdMonthOfQuarter)) {
                return statistics.getComplaints();
            }
            return Meters.ZERO;
        }).reduce(Meters::plus).orElse(Meters.ZERO);
    }

    private Meters calculateOrdersQuarter(List<OrderStatistics> orderStatistics, StatisticsPeriod today) {
        var firstMonthOfQuarter = today.getMonth().firstMonthOfQuarter();
        var secondMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 1);
        var thirdMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        return orderStatistics.stream().map(statistics -> {
            if (statistics.isInMonth(firstMonthOfQuarter) || statistics.isInMonth(secondMonthOfQuarter) || statistics.isInMonth(thirdMonthOfQuarter)) {
                return statistics.getOrders();
            }
            return Meters.ZERO;
        }).reduce(Meters::plus).orElse(Meters.ZERO);
    }

    private Meters calculateComplaintsYear(List<OrderStatistics> orderStatistics, StatisticsPeriod today) {
        return orderStatistics.stream().map(statistics -> {
            if (statistics.getPeriod().getYear().equals(today.getYear())) {
                return statistics.getComplaints();
            }
            return Meters.ZERO;
        }).reduce(Meters::plus).orElse(Meters.ZERO);
    }

    private Meters calculateOrdersYear(List<OrderStatistics> orderStatistics, StatisticsPeriod today) {
        return orderStatistics.stream().map(statistics -> {
            if (statistics.getPeriod().getYear().equals(today.getYear())) {
                return statistics.getOrders();
            }
            return Meters.ZERO;
        }).reduce(Meters::plus).orElse(Meters.ZERO);
    }
}
