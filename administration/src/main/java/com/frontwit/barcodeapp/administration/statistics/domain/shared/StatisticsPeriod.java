package com.frontwit.barcodeapp.administration.statistics.domain.shared;

import lombok.Value;

import java.time.*;

import static java.time.ZoneId.systemDefault;

@Value
public class StatisticsPeriod {
    private int day;
    private Month month;
    private Year year;

    public static StatisticsPeriod of(Instant instant) {
        //TODO tutaj cos zle date zmienia np instant jest 12-06 a zmienia na 13-06
        ZonedDateTime zdt = instant.atZone(systemDefault());
        return new StatisticsPeriod(zdt.getDayOfMonth(), Month.from(zdt), Year.from(zdt));
    }

    public static StatisticsPeriod beginningOfYear(Year year) {
        return new StatisticsPeriod(1, Month.JANUARY, year);
    }

    public Instant toInstant() {
        LocalDate zonedDateTime = LocalDate.of(year.getValue(), month, day);
        return zonedDateTime.atStartOfDay().atZone(systemDefault()).toInstant();
    }
}
