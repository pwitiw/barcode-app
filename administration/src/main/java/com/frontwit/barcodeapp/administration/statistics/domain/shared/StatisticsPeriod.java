package com.frontwit.barcodeapp.administration.statistics.domain.shared;

import lombok.Value;

import java.time.*;

import static java.time.ZoneId.systemDefault;

@Value
public class StatisticsPeriod {
    private final int day;
    private final Month month;
    private final Year year;

    public static StatisticsPeriod of(Instant instant) {
        ZonedDateTime zdt = instant.atZone(systemDefault());
        return new StatisticsPeriod(zdt.getDayOfMonth(), Month.from(zdt), Year.from(zdt));
    }

    public Instant toInstant() {
        LocalDate zonedDateTime = LocalDate.of(year.getValue(), month, day);
        return zonedDateTime.atStartOfDay().atZone(systemDefault()).toInstant();
    }
}
