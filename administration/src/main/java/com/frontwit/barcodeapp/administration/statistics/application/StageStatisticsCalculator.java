package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.processing.front.model.Front;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontStatisticsRepository;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import org.springframework.stereotype.Service;

import static com.frontwit.barcodeapp.administration.statistics.application.StageStatistics.emptyStatistics;

@Service
public class StageStatisticsCalculator {

    private StageStatisticsRepository repository;
    private FrontStatisticsRepository frontStatisticsRepository;

    public void calculateStageStatistics(StatisticsPeriod period, Stage stage, Front front) {
        Shift shift = Shift.from(period);
        StageStatistics statistics = repository.findByShiftAndPeriodAndStage(shift, period, stage)
                .orElse(emptyStatistics(period, stage, shift));
        statistics.addMeters(calculateMeters(front));
        repository.save(statistics);
    }

    private Double calculateMeters(Front front) {
        var frontEntity = frontStatisticsRepository.findByBarcode(front.getBarcode());
        return frontEntity
                .map(entity -> entity.getHeight() * entity.getWidth() / 1000000.0)
                .orElse(0.0);
    }
}
