package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.processing.synchronization.OrderSynchronizer;
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatisticsRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoricalStatisticsSynchronizer {
    private final OrderSynchronizer orderSynchronizer;
    private final OrderStatisticsRepository orderStatisticsRepository;

    @Scheduled(initialDelay = 1_000, fixedDelay = Long.MAX_VALUE)
    void synchronize() {
        if (orderStatisticsRepository.empty()) {
            orderSynchronizer.synchronizeStatistics();
        }
    }
}
