package com.frontwit.barcodeapp.administration.statistics.application;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HourlyStatisticsDto {
    Double meters;
    int hour;
}
