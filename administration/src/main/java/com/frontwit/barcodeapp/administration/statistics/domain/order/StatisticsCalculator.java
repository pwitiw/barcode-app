package com.frontwit.barcodeapp.administration.statistics.domain.order;

import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;

import java.util.List;
import java.util.function.Function;

public interface StatisticsCalculator {

    Meters calculate(List<OrderStatistics> orderStatistics, StatisticsPeriod period, Function<OrderStatistics, Meters> orderTypeMapper);
}
