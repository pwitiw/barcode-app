package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;


@AllArgsConstructor
@Component
public class MongoStageStatisticsRepository implements StageStatisticsRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public void save(StageStatistics statistics) {
        var entity = PackingStatisticsEntity.of(statistics);
        mongoTemplate.save(entity);
    }

    public Optional<StageStatistics> findByShiftAndPeriodAndStage(Shift shift, StatisticsPeriod period, Stage stage) {
        return mongoTemplate.find(query(shift, period, stage), StageStatistics.class).stream().findFirst();
    }

    private Query query(Shift shift, StatisticsPeriod statisticsPeriod, Stage stage) {
        Criteria c = new Criteria().andOperator(Criteria.where("statisticsPeriod").is(statisticsPeriod),
                Criteria.where("stage").is(stage), Criteria.where("shift").is(shift));
        return new Query(c);
    }

}