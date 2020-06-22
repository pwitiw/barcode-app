package com.frontwit.barcodeapp.administration.statistics.domain.order;

import com.frontwit.barcodeapp.administration.processing.order.model.OrderType;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.Getter;

@Getter
public final class OrderStatistics {
    private final StatisticsPeriod period;
    private Meters orders = Meters.ZERO;
    private Meters complainments = Meters.ZERO;

    private OrderStatistics(StatisticsPeriod period) {
        this.period = period;
    }

    public static OrderStatistics of(StatisticsPeriod period) {
        return new OrderStatistics(period);
    }

    public void apply(OrderType type, Meters arg) {
        if (type == OrderType.ORDER) {
            orders = orders.plus(arg);
        } else {
            complainments = complainments.plus(arg);
        }
    }
}
