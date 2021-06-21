package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StageStatistics {
    private Double meters;
    private final StatisticsPeriod period;
    private final Stage stage;
    private final Shift shift;

    static StageStatistics emptyStatistics(StatisticsPeriod period, Stage stage, Shift shift) {
        return new StageStatistics(0.0, period, stage, shift);
    }

    public void addMeters(Double newMeters) {
        meters = meters + newMeters;
    }
}
