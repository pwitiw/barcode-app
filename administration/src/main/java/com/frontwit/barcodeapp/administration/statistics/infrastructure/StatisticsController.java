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
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.Month;
import java.time.Year;

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
    public StageStatisticsDto findStatistics2() {
//            (@RequestParam StatisticsPeriod period, @RequestParam Stage stage) {
        StatisticsPeriod period1 = new StatisticsPeriod(15, 30, Month.JUNE, Year.now());
        Stage stage1 = Stage.PACKING;
        return stageStatisticsCalculator.statisticsFor(period1, stage1);
    }
}
