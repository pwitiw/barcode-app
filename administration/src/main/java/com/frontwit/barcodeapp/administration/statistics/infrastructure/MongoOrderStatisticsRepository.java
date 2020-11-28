package com.frontwit.barcodeapp.administration.statistics.infrastructure;

import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatistics;
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatisticsRepository;
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
public class MongoOrderStatisticsRepository implements OrderStatisticsRepository {

    private static final String PERIOD_FIELD = "period";
    private final MongoTemplate mongoTemplate;

    @Override
    public void save(OrderStatistics orderStatistics) {
        var entity = OrderStatisticsEntity.of(orderStatistics);
        mongoTemplate.save(entity);
    }

    @Override
    public Optional<OrderStatistics> findBy(StatisticsPeriod statisticsPeriod) {
        return Optional.empty();
    }

    @Override
    public List<OrderStatistics> findForYearUntil(StatisticsPeriod period) {
        var beginningOfYear = StatisticsPeriod.beginningOfYear(period.getYear()).toInstant();
        var statisticsEntity = mongoTemplate.find(forGivenYear(period, beginningOfYear), OrderStatisticsEntity.class);
        return statisticsEntity.stream()
                .map(OrderStatisticsEntity::toOrderStatics)
                .collect(Collectors.toList());
    }

    @Override
    public boolean empty() {
        return mongoTemplate.count(new Query(), OrderStatisticsEntity.class) == 0;
    }

    private Query forGivenYear(StatisticsPeriod period, Instant beginningOfYear) {
        Criteria c = new Criteria().andOperator(Criteria.where(PERIOD_FIELD).lte(period.toInstant()),
                Criteria.where(PERIOD_FIELD).gte(beginningOfYear));
        return new Query(c);
    }
}
