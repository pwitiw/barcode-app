package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.statistics.domain.order.*;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@AllArgsConstructor
@Service
public class OrderStatisticsCreator {

    private final OrderStatisticsRepository orderStatisticsRepository;

    public OrderStatisticsDto statisticsFor(StatisticsPeriod today) {
        DailyStatisticsCalculator dailyStatisticsCalculator = new DailyStatisticsCalculator();
        WeeklyStatisticsCalculator weeklyStatisticsCalculator = new WeeklyStatisticsCalculator();
        MonthlyStatisticsCalculator monthlyStatisticsCalculator = new MonthlyStatisticsCalculator();
        QuarterlyStatisticsCalculator quarterlyStatisticsCalculator = new QuarterlyStatisticsCalculator();
        YearlyStatisticsCalculator yearlyStatisticsCalculator = new YearlyStatisticsCalculator();

        OrderStatisticsDto dto = new OrderStatisticsDto();
        var orderStatistics = orderStatisticsRepository.findForYearUntil(today);
        dto.setPeriods(
                Arrays.asList(
                        OrderStatisticsDto.PeriodDto.of(PeriodType.TODAY, dailyStatisticsCalculator.calculate(orderStatistics, today, OrderStatistics::getOrders), dailyStatisticsCalculator.calculate(orderStatistics, today, OrderStatistics::getComplaints)),
                        OrderStatisticsDto.PeriodDto.of(PeriodType.WEEK, weeklyStatisticsCalculator.calculate(orderStatistics, today, OrderStatistics::getOrders), weeklyStatisticsCalculator.calculate(orderStatistics, today, OrderStatistics::getComplaints)),
                        OrderStatisticsDto.PeriodDto.of(PeriodType.MONTH, monthlyStatisticsCalculator.calculate(orderStatistics, today, OrderStatistics::getOrders), monthlyStatisticsCalculator.calculate(orderStatistics, today, OrderStatistics::getComplaints)),
                        OrderStatisticsDto.PeriodDto.of(PeriodType.QUARTER, quarterlyStatisticsCalculator.calculate(orderStatistics, today, OrderStatistics::getOrders), quarterlyStatisticsCalculator.calculate(orderStatistics, today, OrderStatistics::getComplaints)),
                        OrderStatisticsDto.PeriodDto.of(PeriodType.YEAR, yearlyStatisticsCalculator.calculate(orderStatistics, today, OrderStatistics::getOrders), yearlyStatisticsCalculator.calculate(orderStatistics, today, OrderStatistics::getComplaints))
                )
        );
        return dto;
    }

}
