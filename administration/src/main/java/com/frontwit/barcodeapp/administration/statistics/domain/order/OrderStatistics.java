package com.frontwit.barcodeapp.administration.statistics.domain.order;

import com.frontwit.barcodeapp.administration.processing.order.model.OrderType;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.Getter;

import java.time.Month;

@Getter
public final class OrderStatistics {
    private final StatisticsPeriod period;
    private Meters orders;
    private Meters complaints;

    private OrderStatistics(StatisticsPeriod period, Meters orders, Meters complaints) {
        this.period = period;
        this.orders = orders;
        this.complaints = complaints;
    }

    public static OrderStatistics of(StatisticsPeriod period) {
        return OrderStatistics.of(period, Meters.ZERO, Meters.ZERO);
    }

    public static OrderStatistics of(StatisticsPeriod period, Meters orders, Meters complaints) {
        return new OrderStatistics(period, orders, complaints);
    }

    public void apply(final OrderType type, final Meters meters) {
        if (type == OrderType.ORDER) {
            orders = orders.plus(meters);
        } else {
            complaints = complaints.plus(meters);
        }
    }

    public boolean isInPeriod(StatisticsPeriod period) {
        return this.period.equals(period);
    }

    public boolean isInMonth(Month month) {
        return this.period.getMonth().equals(month);
    }
}
