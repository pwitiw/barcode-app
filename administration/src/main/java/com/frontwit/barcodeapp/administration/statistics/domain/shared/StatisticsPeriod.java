package com.frontwit.barcodeapp.administration.statistics.domain.shared;

import lombok.Value;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static java.time.DayOfWeek.MONDAY;

@Value
public class StatisticsPeriod {
    private static final ZoneOffset ZONE = ZoneOffset.UTC;
    private int hour;
    private int day;
    private Month month;
    private Year year;

    public static StatisticsPeriod dailyFrom(Instant instant) {
        ZonedDateTime zdt = instant.atZone(ZONE);
        return new StatisticsPeriod(0, zdt.getDayOfMonth(), Month.from(zdt), Year.from(zdt));
    }

    public static StatisticsPeriod hourlyFrom(ZonedDateTime time) {
        return new StatisticsPeriod(time.getHour(), time.getDayOfMonth(), time.getMonth(), Year.from(time));
    }

    public static StatisticsPeriod from(Instant instant) {
        ZonedDateTime zdt = instant.atZone(ZONE);
        return new StatisticsPeriod(zdt.getHour(), zdt.getDayOfMonth(), Month.from(zdt), Year.from(zdt));
    }

    public static StatisticsPeriod beginningOfYear(Year year) {
        return new StatisticsPeriod(1, 1, Month.JANUARY, year);
    }

    public StatisticsPeriod beginningOfWeek() {
        var dayOfWeek = toInstant().atZone(ZONE).getDayOfWeek().getValue();
        var firstDayOfWeek = toInstant().minus(dayOfWeek - MONDAY.getValue(), ChronoUnit.DAYS);
        return StatisticsPeriod.dailyFrom(firstDayOfWeek);
    }

    public StatisticsPeriod endOfWeek() {
        return StatisticsPeriod.dailyFrom(beginningOfWeek().toInstant().plus(6, ChronoUnit.DAYS));
    }

    public StatisticsPeriod beginningOfDay() {
        return new StatisticsPeriod(0, day, month, year);
    }

    public StatisticsPeriod endOfDay() {
        return new StatisticsPeriod(23, day, month, year);
    }

    public Instant toInstant() {
        var localDate = LocalDate.of(year.getValue(), month, day);
        var localTime = LocalTime.of(hour, 0);
        return ZonedDateTime.of(LocalDateTime.of(localDate, localTime), ZONE).toInstant();
    }
}
