package com.frontwit.barcodeapp.administration.statistics.infrastructure;

import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatistics;
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatisticsRepository;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class MongoOrderStatisticsRepository implements OrderStatisticsRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public void save(OrderStatistics orderStatistics) {
        var entity = OrderStatisticsEntity.of(orderStatistics);
        mongoTemplate.save(entity);
    }

    @Override
    public Optional<OrderStatistics> findBy(StatisticsPeriod statisticsPeriod) {
        return Optional.empty();
    }
}
