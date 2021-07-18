package com.frontwit.barcodeapp.administration.statistics.infrastructure;

import com.frontwit.barcodeapp.api.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.application.StageStatistics;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import com.frontwit.barcodeapp.administration.statistics.domain.stage.StageStatisticsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@AllArgsConstructor
@Component
public class MongoStageStatisticsRepository implements StageStatisticsRepository {
    private static final String STAGE = "stage";
    private static final String PERIOD = "period";

    private final MongoTemplate mongoTemplate;

    @Override
    public void save(StageStatistics statistics) {
        var entity = StageStatisticsEntity.of(statistics);
        mongoTemplate.save(entity);
    }

    @Override
    public List<StageStatistics> findByPeriodAndStageDaily(StatisticsPeriod period, Stage stage) {
        return mongoTemplate.find(dailyQuery(period, stage), StageStatisticsEntity.class).stream()
                .map(StageStatisticsEntity::toStageStatistics)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StageStatistics> findByPeriodAndStageHourly(StatisticsPeriod period, Stage stage) {
        return mongoTemplate.find(hourlyDaily(period, stage), StageStatisticsEntity.class).stream()
                .findFirst()
                .map(StageStatisticsEntity::toStageStatistics);
    }

    private Query dailyQuery(StatisticsPeriod statisticsPeriod, Stage stage) {
        return periodBetween(statisticsPeriod.beginningOfDay().toInstant(), statisticsPeriod.endOfDay().toInstant())
                .addCriteria(Criteria.where(STAGE).is(stage));
    }

    private Query periodBetween(Instant from, Instant to) {
        return new Query(
                new Criteria().andOperator(
                        Criteria.where(PERIOD).lte(to),
                        Criteria.where(PERIOD).gte(from)
                )
        );
    }

    private Query hourlyDaily(StatisticsPeriod statisticsPeriod, Stage stage) {
        return new Query(
                Criteria.where(PERIOD).is(statisticsPeriod.toInstant())
                        .and(STAGE).is(stage)
        );
    }
}
