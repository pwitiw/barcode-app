package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;

import java.util.Optional;

public interface StageStatisticsRepository {
    void save(StageStatistics statistics);

    Optional<StageStatistics> findByShiftAndPeriodAndStage(Shift shift, StatisticsPeriod period, Stage stage);
}
