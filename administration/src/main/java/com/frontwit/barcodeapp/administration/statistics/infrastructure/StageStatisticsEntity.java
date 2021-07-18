package com.frontwit.barcodeapp.administration.statistics.infrastructure;

import com.frontwit.barcodeapp.api.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.application.StageStatistics;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Data
@Document(collection = "stages")
@NoArgsConstructor
public class StageStatisticsEntity {
    @Id
    private String id;
    private Double meters;
    private Instant period;
    private Stage stage;

    public static StageStatisticsEntity of(StageStatistics statistics) {
        var entity = new StageStatisticsEntity();
        entity.setId(statistics.getId().toString());
        entity.setMeters(statistics.getMeters());
        entity.setPeriod(statistics.getPeriod().toInstant());
        entity.setStage(statistics.getStage());
        return entity;
    }

    public StageStatistics toStageStatistics() {
        return StageStatistics.of(UUID.fromString(id), meters, StatisticsPeriod.from(period), stage);
    }
}
