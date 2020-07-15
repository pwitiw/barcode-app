package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.statistics.application.OrderStatisticsDto.PeriodDto;
import com.frontwit.barcodeapp.administration.statistics.domain.order.*;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.frontwit.barcodeapp.administration.statistics.application.PeriodType.*;

@Service
public class OrderStatisticsCreator {

    private final OrderStatisticsRepository orderStatisticsRepository;
    private final Map<PeriodType, StatisticsCalculator> calculators = new HashMap<>();

    public OrderStatisticsCreator(OrderStatisticsRepository orderStatisticsRepository) {
        this.orderStatisticsRepository = orderStatisticsRepository;
        calculators.put(TODAY, new DailyStatisticsCalculator());
        calculators.put(WEEK, new WeeklyStatisticsCalculator());
        calculators.put(MONTH, new MonthlyStatisticsCalculator());
        calculators.put(QUARTER, new QuarterlyStatisticsCalculator());
        calculators.put(YEAR, new YearlyStatisticsCalculator());
    }

    public OrderStatisticsDto statisticsFor(StatisticsPeriod today) {

        OrderStatisticsDto dto = new OrderStatisticsDto();
        var orderStatistics = orderStatisticsRepository.findForYearUntil(today);
        List<PeriodDto> periods = calculators.entrySet().stream()
                .map(entry -> {
                    var type = entry.getKey();
                    var calculator = entry.getValue();
                    return PeriodDto.of(
                            type,
                            calculator.calculate(orderStatistics, today, OrderStatistics::getOrders),
                            calculator.calculate(orderStatistics, today, OrderStatistics::getComplaints));
                })
                .collect(Collectors.toList());
        dto.setPeriods(periods);
        return dto;
    }

}
