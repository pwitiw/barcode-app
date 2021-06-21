package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "stageStatistics")
public class PackingStatisticsEntity {
    @Id
    private String id = UUID.randomUUID().toString();
    private Double meters;
    private StatisticsPeriod period;
    private Stage stage;
    private Shift shift;

    public static PackingStatisticsEntity of(StageStatistics statistics) {
        var entity = new PackingStatisticsEntity();
        entity.setMeters(statistics.getMeters());
        entity.setPeriod(statistics.getPeriod());
        entity.setShift(statistics.getShift());
        entity.setShift(statistics.getShift());
        return entity;
    }
}
