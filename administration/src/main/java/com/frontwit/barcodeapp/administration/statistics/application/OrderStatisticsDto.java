package com.frontwit.barcodeapp.administration.statistics.application;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class OrderStatisticsDto {
    private List<PeriodDto> periods;

    @Data
    @AllArgsConstructor
    static class PeriodDto {
        private PeriodType type;
        private Double orders;
        private Double complaints;
    }
}
