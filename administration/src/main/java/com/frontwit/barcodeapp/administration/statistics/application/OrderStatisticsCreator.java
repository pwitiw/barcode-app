package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.statistics.application.OrderStatisticsDto.PeriodDto;
import com.frontwit.barcodeapp.administration.statistics.domain.order.*;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.frontwit.barcodeapp.administration.statistics.application.PeriodType.*;

@AllArgsConstructor
@Service
public class OrderStatisticsCreator {

    private final OrderStatisticsRepository orderStatisticsRepository;

    public OrderStatisticsDto statisticsFor(StatisticsPeriod today) {
        DailyStatisticsCalculator daily = new DailyStatisticsCalculator();
        WeeklyStatisticsCalculator weekly = new WeeklyStatisticsCalculator();
        MonthlyStatisticsCalculator monthly = new MonthlyStatisticsCalculator();
        QuarterlyStatisticsCalculator quarterly = new QuarterlyStatisticsCalculator();
        YearlyStatisticsCalculator yearly = new YearlyStatisticsCalculator();

        OrderStatisticsDto dto = new OrderStatisticsDto();
        var orderStatistics = orderStatisticsRepository.findForYearUntil(today);
        dto.setPeriods(
                Arrays.asList(
                        PeriodDto.of(TODAY, daily.calculate(orderStatistics, today, OrderStatistics::getOrders),
                                daily.calculate(orderStatistics, today, OrderStatistics::getComplaints)),
                        PeriodDto.of(WEEK, weekly.calculate(orderStatistics, today, OrderStatistics::getOrders),
                                weekly.calculate(orderStatistics, today, OrderStatistics::getComplaints)),
                        PeriodDto.of(MONTH, monthly.calculate(orderStatistics, today, OrderStatistics::getOrders),
                                monthly.calculate(orderStatistics, today, OrderStatistics::getComplaints)),
                        PeriodDto.of(QUARTER, quarterly.calculate(orderStatistics, today, OrderStatistics::getOrders),
                                quarterly.calculate(orderStatistics, today, OrderStatistics::getComplaints)),
                        PeriodDto.of(YEAR, yearly.calculate(orderStatistics, today, OrderStatistics::getOrders),
                                yearly.calculate(orderStatistics, today, OrderStatistics::getComplaints))
                )
        );
        return dto;
    }

}
