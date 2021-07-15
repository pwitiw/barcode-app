package com.frontwit.barcodeapp.administration.statistics.infrastructure;

import com.frontwit.barcodeapp.administration.processing.front.model.FrontProcessed;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontStatisticsRepository;
import com.frontwit.barcodeapp.administration.statistics.application.StageStatistics;
import com.frontwit.barcodeapp.administration.statistics.domain.OrderPlaced;
import com.frontwit.barcodeapp.administration.statistics.domain.order.Meters;
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatistics;
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatisticsRepository;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import com.frontwit.barcodeapp.administration.statistics.domain.stage.StageStatisticsRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@Service
@AllArgsConstructor
public class StatisticsEventsHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsEventsHandler.class);

    private final OrderStatisticsRepository orderStatisticsRepository;
    private final StageStatisticsRepository stageStatisticsRepository;
    private final FrontStatisticsRepository frontStatisticsRepository;

    @EventListener
    public void handle(OrderPlaced event) {
        var period = StatisticsPeriod.dailyFrom(event.getOrderedAt());
        var statistics = orderStatisticsRepository.findBy(period).orElse(OrderStatistics.of(period));
        statistics.apply(event.getOrderType(), event.getMeters());
        orderStatisticsRepository.save(statistics);
    }

    @EventListener
    public void handle(FrontProcessed event) {
        frontStatisticsRepository.findByBarcode(event.getBarcode()).ifPresent(front -> {
            var date = event.getDateTime().atZone(ZoneId.of("Europe/Warsaw"));
            StatisticsPeriod period = StatisticsPeriod.hourlyFrom(date);
            var statistics = stageStatisticsRepository.findByPeriodAndStageHourly(StatisticsPeriod.hourlyFrom(date), event.getStage())
                    .orElse(StageStatistics.emptyStatistics(period, event.getStage()));
            statistics.add(Meters.ofMilimeters(front.getHeight(), front.getWidth()));
            stageStatisticsRepository.save(statistics);
        });
    }
}
