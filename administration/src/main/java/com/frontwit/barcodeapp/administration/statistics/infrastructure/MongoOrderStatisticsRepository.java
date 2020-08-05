package com.frontwit.barcodeapp.administration.statistics.infrastructure;

import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatistics;
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatisticsRepository;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.Month;
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
        var beginningOfYear = new StatisticsPeriod(2, Month.JANUARY, period.getYear()).toInstant();
        Criteria c = new Criteria().andOperator(Criteria.where(PERIOD_FIELD).lte(period.toInstant()),
                Criteria.where(PERIOD_FIELD).gte(beginningOfYear));
        Query forGivenYear = new Query(c);
        var statisticsEntity = mongoTemplate.find(forGivenYear, OrderStatisticsEntity.class);
        return statisticsEntity.stream()
                .map(entity -> OrderStatistics.of(StatisticsPeriod.of(entity.getPeriod()), entity.getOrders(), entity.getComplainments()))
                .collect(Collectors.toList());
    }
}
