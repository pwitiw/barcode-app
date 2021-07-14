package com.frontwit.barcodeapp.administration.statistics.infrastructure;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.application.OrderStatisticsCalculator;
import com.frontwit.barcodeapp.administration.statistics.application.OrderStatisticsDto;
import com.frontwit.barcodeapp.administration.statistics.application.StageStatisticsCalculator;
import com.frontwit.barcodeapp.administration.statistics.application.StageStatisticsDto;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class StatisticsController {

    private final OrderStatisticsCalculator statisticsCreator;
    private final StageStatisticsCalculator stageStatisticsCalculator;

    @GetMapping("/statistics/orders")
    public OrderStatisticsDto findStatistics() {
        return statisticsCreator.statisticsFor(StatisticsPeriod.dailyFrom(Instant.now()));
    }

    @GetMapping("/statistics/stage")
    public StageStatisticsDto findStatisticsFor(@RequestParam Long date, @RequestParam Stage stage) {
        StatisticsPeriod period1 = StatisticsPeriod.from(Instant.ofEpochMilli(date));
        return stageStatisticsCalculator.statisticsFor(period1, stage);
    }
}
