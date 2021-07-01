package com.frontwit.barcodeapp.administration.statistics.infrastructure;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.application.StageStatistics;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;

import java.util.List;
import java.util.Optional;

public interface StageStatisticsRepository {
    void save(StageStatistics statistics);

    List<StageStatistics> findByPeriodAndStageDaily(StatisticsPeriod period, Stage stage);

    Optional<StageStatistics> findByPeriodAndStageHourly(StatisticsPeriod period, Stage stage);

}
