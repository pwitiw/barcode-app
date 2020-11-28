package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.statistics.application.OrderStatisticsDto.PeriodDto;
import com.frontwit.barcodeapp.administration.statistics.domain.order.*;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.frontwit.barcodeapp.administration.statistics.application.PeriodType.*;

@Service
public class OrderStatisticsCalculator {

    private static final Map<PeriodType, StatisticsCalculator> CALCULATORS = new LinkedHashMap<>() {{
        put(TODAY, new DailyStatisticsCalculator());
        put(WEEK, new WeeklyStatisticsCalculator());
        put(MONTH, new MonthlyStatisticsCalculator());
        put(QUARTER, new QuarterlyStatisticsCalculator());
        put(YEAR, new YearlyStatisticsCalculator());
    }};
    private final OrderStatisticsRepository orderStatisticsRepository;

    public OrderStatisticsCalculator(OrderStatisticsRepository orderStatisticsRepository) {
        this.orderStatisticsRepository = orderStatisticsRepository;
    }

    public OrderStatisticsDto statisticsFor(StatisticsPeriod today) {
        var orderStatistics = orderStatisticsRepository.findForYearUntil(today);
        List<PeriodDto> periods = CALCULATORS.entrySet().stream()
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
