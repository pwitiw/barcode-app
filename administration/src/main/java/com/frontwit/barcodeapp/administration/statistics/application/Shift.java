package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;

public enum Shift {
    FIRST,
    SECOND;

    static Shift from(StatisticsPeriod period) {
        return period.getHour() < 14 ? Shift.FIRST : Shift.SECOND;
    }
}