package com.frontwit.barcodeapp.administration.statistics.domain.shared;

import lombok.Value;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static java.time.DayOfWeek.MONDAY;

@Value
public class StatisticsPeriod {
    private static final ZoneOffset ZONE = ZoneOffset.UTC;
    private int day;
    private Month month;
    private Year year;

    public static StatisticsPeriod of(Instant instant) {
        ZonedDateTime zdt = instant.atZone(ZONE);
        return new StatisticsPeriod(zdt.getDayOfMonth(), Month.from(zdt), Year.from(zdt));
    }

    public static StatisticsPeriod beginningOfYear(Year year) {
        return new StatisticsPeriod(1, Month.JANUARY, year);
    }

    public StatisticsPeriod beginningOfWeek() {
        var dayOfWeek = toInstant().atZone(ZONE).getDayOfWeek().getValue();
        var firstDayOfWeek = toInstant().minus(dayOfWeek - MONDAY.getValue(), ChronoUnit.DAYS);
        return StatisticsPeriod.of(firstDayOfWeek);
    }

    public StatisticsPeriod endOfWeek() {
        return StatisticsPeriod.of(beginningOfWeek().toInstant().plus(6, ChronoUnit.DAYS));
    }

    public Instant toInstant() {
        LocalDate zonedDateTime = LocalDate.of(year.getValue(), month, day);
        return zonedDateTime.atStartOfDay().atZone(ZONE).toInstant();
    }
}
