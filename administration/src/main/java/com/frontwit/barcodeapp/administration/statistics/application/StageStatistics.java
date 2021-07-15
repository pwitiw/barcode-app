package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.domain.order.Meters;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Data
public class StageStatistics {
    private UUID id;
    private Double meters;
    private StatisticsPeriod period;
    private Stage stage;

    public static StageStatistics emptyStatistics(StatisticsPeriod period, Stage stage) {
        return new StageStatistics(UUID.randomUUID(), 0.0, period, stage);
    }

    public static StageStatistics of(UUID id, Double meters, StatisticsPeriod period, Stage stage) {
        return new StageStatistics(id, meters, period, stage);
    }

    public void add(Meters meters) {
        this.meters += meters.getValue();
    }
}
