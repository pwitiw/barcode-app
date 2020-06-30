package com.frontwit.barcodeapp.administration.statistics.domain.order;

import com.frontwit.barcodeapp.administration.processing.order.model.OrderType;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.Getter;

@Getter
public final class OrderStatistics {
    private final StatisticsPeriod period;
    private Meters orders = Meters.ZERO;
    private Meters complaints = Meters.ZERO;

    private OrderStatistics(StatisticsPeriod period) {
        this.period = period;
    }

    public static OrderStatistics of(StatisticsPeriod period) {
        return new OrderStatistics(period);
    }

    public void apply(final OrderType type, final Meters meters) {
        if (type == OrderType.ORDER) {
            orders = orders.plus(meters);
        } else {
            complaints = complaints.plus(meters);
        }
    }
}
