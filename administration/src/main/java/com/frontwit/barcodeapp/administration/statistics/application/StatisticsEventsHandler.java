package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.statistics.domain.OrderPlaced;
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatistics;
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatisticsRepository;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatisticsEventsHandler {

    private final OrderStatisticsRepository orderStatisticsRepository;

    public void handle(OrderPlaced event) {
        var period = StatisticsPeriod.of(event.getOrderedAt());
        var statistics = orderStatisticsRepository.findBy(period).orElse(OrderStatistics.of(period));
        statistics.apply(event.getOrderType(), event.getMeters());
        orderStatisticsRepository.save(statistics);
    }
}
