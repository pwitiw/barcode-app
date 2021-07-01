package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.processing.front.model.Front;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontProcessed;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontRepository;
import com.frontwit.barcodeapp.administration.statistics.domain.OrderPlaced;
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatistics;
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatisticsRepository;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StatisticsEventsHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsEventsHandler.class);

    private final OrderStatisticsRepository orderStatisticsRepository;
    private final StageStatisticsCalculator stageStatisticsCalculator;
    private final FrontRepository frontRepository;

    @EventListener
    public void handle(OrderPlaced event) {
        var period = StatisticsPeriod.dailyFrom(event.getOrderedAt());
        var statistics = orderStatisticsRepository.findBy(period).orElse(OrderStatistics.of(period));
        statistics.apply(event.getOrderType(), event.getMeters());
        orderStatisticsRepository.save(statistics);
    }

    @EventListener
    public void handle(FrontProcessed event) {
        Optional<Front> front = frontRepository.findBy(event.getBarcode());
        if (front.isEmpty()) {
            LOGGER.info("Front with barcode: {} was not found", event.getBarcode());
            return;
        }
        var date = event.getDateTime().atZone(ZoneId.of("Europe/Warsaw"));
        StatisticsPeriod period = StatisticsPeriod.hourlyFrom(date);
        stageStatisticsCalculator.saveStageStatistics(period, event.getStage(), front.get());
    }
}
