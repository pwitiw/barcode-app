package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.statistics.application.OrderStatisticsDto.PeriodDto;
import com.frontwit.barcodeapp.administration.statistics.domain.order.*;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.frontwit.barcodeapp.administration.statistics.application.PeriodType.*;

@Service
public class OrderStatisticsCreator {

    private final OrderStatisticsRepository orderStatisticsRepository;
    private final Map<PeriodType, StatisticsCalculator> calculators = Map.of(
            TODAY, new DailyStatisticsCalculator(),
            WEEK, new WeeklyStatisticsCalculator(),
            MONTH, new MonthlyStatisticsCalculator(),
            QUARTER, new QuarterlyStatisticsCalculator(),
            YEAR, new YearlyStatisticsCalculator());

    public OrderStatisticsCreator(OrderStatisticsRepository orderStatisticsRepository) {
        this.orderStatisticsRepository = orderStatisticsRepository;
    }

    public OrderStatisticsDto statisticsFor(StatisticsPeriod today) {
        var orderStatistics = orderStatisticsRepository.findForYearUntil(today);
        List<PeriodDto> periods = calculators.entrySet().stream()
                .map(entry -> createPeriodDto(today, orderStatistics, entry))
                .collect(Collectors.toList());
        return new OrderStatisticsDto(periods);
    }

    private PeriodDto createPeriodDto(StatisticsPeriod today,
                                      List<OrderStatistics> orderStatistics,
                                      Map.Entry<PeriodType, StatisticsCalculator> entry) {
        var type = entry.getKey();
        var calculator = entry.getValue();
        return PeriodDto.of(
                type,
                calculator.calculate(orderStatistics, today, OrderStatistics::getOrders),
                calculator.calculate(orderStatistics, today, OrderStatistics::getComplaints));
    }
}
