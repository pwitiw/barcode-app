package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.api.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import com.frontwit.barcodeapp.administration.statistics.domain.stage.StageStatisticsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StageStatisticsCalculator {

    private static final Double ZERO_VALUE = 0.0;
    private static final int SHIFT_CHANGE_HOUR = 14;

    private final StageStatisticsRepository repository;

    public StageStatisticsDto statisticsFor(StatisticsPeriod period, Stage stage) {
        List<StageStatistics> statistics = repository.findByPeriodAndStageDaily(period, stage);
        if (statistics.isEmpty()) {
            List<HourlyStatisticsDto> hourlyDto = new ArrayList<>();
            return new StageStatisticsDto(hourlyDto, stage, period, ZERO_VALUE, ZERO_VALUE);
        }
        List<HourlyStatisticsDto> hourlyStatisticsDtos = statistics.stream()
                .map(s -> new HourlyStatisticsDto(s.getMeters(), s.getPeriod().getHour()))
                .sorted(Comparator.comparing(HourlyStatisticsDto::getHour))
                .collect(Collectors.toList());

        Double firstShift = hourlyStatisticsDtos.stream()
                .filter(f -> f.getHour() < SHIFT_CHANGE_HOUR)
                .map(HourlyStatisticsDto::getMeters)
                .reduce(Double::sum)
                .orElse(ZERO_VALUE);
        Double secondShift = hourlyStatisticsDtos.stream()
                .filter(f -> f.getHour() >= SHIFT_CHANGE_HOUR)
                .map(HourlyStatisticsDto::getMeters)
                .reduce(Double::sum)
                .orElse(ZERO_VALUE);
        return StageStatisticsDto.of(hourlyStatisticsDtos, stage, period, firstShift, secondShift);
    }
}
