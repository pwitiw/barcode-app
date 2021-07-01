package com.frontwit.barcodeapp.administration.statistics.infrastructure;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.application.StageStatistics;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
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
                .addCriteria(Criteria.where("stage").is(stage));
    }

    private Query periodBetween(Instant from, Instant to) {
        return new Query(
                new Criteria().andOperator(
                        Criteria.where("period").lte(to),
                        Criteria.where("period").gte(from)
                )
        );
    }

    private Query hourlyDaily(StatisticsPeriod statisticsPeriod, Stage stage) {
        return new Query(
                Criteria.where("period").is(statisticsPeriod.toInstant())
                        .and("stage").is(stage)
        );
    }
}