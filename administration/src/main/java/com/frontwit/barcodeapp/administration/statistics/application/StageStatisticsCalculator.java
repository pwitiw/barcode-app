package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.processing.front.model.Front;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontProcessed;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontRepository;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontStatisticsRepository;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Year;

import static com.frontwit.barcodeapp.administration.statistics.application.StageStatistics.emptyStatistics;

public class StageStatisticsCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(StageStatisticsCalculator.class);

    private StageStatisticsRepository repository;
    private FrontRepository frontRepository;
    private FrontStatisticsRepository frontStatisticsRepository;


    public void calculateStageStatistics(FrontProcessed event) {
        var front = frontRepository.findBy(event.getBarcode());
        if (front.isEmpty()) {
            LOGGER.info("Nie znaleziono frontu dla barcode: {}", event.getBarcode());
            return;
        }
        var date = event.getDateTime();
        StatisticsPeriod period = new StatisticsPeriod(date.getDayOfMonth(), date.getMonth(), Year.of(date.getYear()));
        var hour = date.getHour();
        var shift = calculateShift(hour);
        Stage stage = event.getStage();
        StageStatistics statistics = repository.findByShiftAndPeriodAndStage(shift, period, event.getStage())
                .orElse(emptyStatistics(period, stage, shift));
        statistics.addMeters(calculateMeters(front.get()));
        repository.save(statistics);
    }

    private Shift calculateShift(int hour) {
        return hour >= 6 && hour <= 13 ? Shift.FIRST : Shift.SECOND;
    }

    private Double calculateMeters(Front front) {
        var frontEntity = frontStatisticsRepository.findByBarcode(front.getBarcode());
        return frontEntity
                .map(entity -> entity.getHeight() * entity.getWidth() / 1000000.0)
                .orElse(0.0);
    }
}
