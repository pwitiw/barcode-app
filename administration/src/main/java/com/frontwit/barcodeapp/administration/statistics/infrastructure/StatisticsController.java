package com.frontwit.barcodeapp.administration.statistics.infrastructure;

import com.frontwit.barcodeapp.administration.statistics.application.OrderStatisticsCalculator;
import com.frontwit.barcodeapp.administration.statistics.application.OrderStatisticsDto;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class StatisticsController {

    private final OrderStatisticsCalculator statisticsCreator;

    @GetMapping("/statistics/orders")
    public OrderStatisticsDto findStatistics() {
        return statisticsCreator.statisticsFor(StatisticsPeriod.of(Instant.now()));
    }
}
