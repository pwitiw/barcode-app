package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.statistics.domain.order.Meters;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderStatisticsDto {
    private List<PeriodDto> periods;

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class PeriodDto {
        private PeriodType type;
        private Double orders;
        private Double complaints;

        public static PeriodDto of(PeriodType type, Meters orders, Meters complaints) {
            return new PeriodDto(type, orders.getValue(), complaints.getValue());
        }
    }
}
