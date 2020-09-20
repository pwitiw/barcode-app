package com.frontwit.barcodeapp.administration.statistics.infrastructure;


import com.frontwit.barcodeapp.administration.statistics.domain.order.Meters;
import com.frontwit.barcodeapp.administration.statistics.domain.order.OrderStatistics;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "statistics")
public class OrderStatisticsEntity {
    @Id
    private String id = UUID.randomUUID().toString();
    private Meters orders;
    private Meters complainments;
    private Instant period;

    static OrderStatisticsEntity of(OrderStatistics orderStatistics) {
        var entity = new OrderStatisticsEntity();
        entity.setOrders(orderStatistics.getOrders());
        entity.setComplainments(orderStatistics.getComplaints());
        entity.setPeriod(orderStatistics.getPeriod().toInstant());
        return entity;
    }

    public OrderStatistics toOrderStatics() {
        return OrderStatistics.of(StatisticsPeriod.of(period), orders, complainments);
    }
}


