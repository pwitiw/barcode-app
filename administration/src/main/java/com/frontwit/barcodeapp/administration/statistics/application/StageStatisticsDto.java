package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StageStatisticsDto {
    private List<HourlyStatisticsDto> hourlyStatistics;
    private Stage stage;
    private StatisticsPeriod period;
    private Double firstShift;
    private Double secondShift;

    public static StageStatisticsDto of(List<HourlyStatisticsDto> hourlyDto,
                                        Stage stage,
                                        StatisticsPeriod period,
                                        Double firstShift,
                                        Double secondShift) {
        StageStatisticsDto dto = new StageStatisticsDto();
        dto.setStage(stage);
        dto.setHourlyStatistics(hourlyDto);
        dto.setPeriod(period);
        dto.setFirstShift(firstShift);
        dto.setSecondShift(secondShift);
        return dto;
    }
}
