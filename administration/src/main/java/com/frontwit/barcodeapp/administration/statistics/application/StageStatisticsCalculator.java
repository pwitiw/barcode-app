package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.processing.front.model.Front;
import com.frontwit.barcodeapp.administration.processing.front.model.FrontStatisticsRepository;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.statistics.domain.shared.StatisticsPeriod;
import com.frontwit.barcodeapp.administration.statistics.infrastructure.StageStatisticsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.frontwit.barcodeapp.administration.statistics.application.StageStatistics.emptyStatistics;

@AllArgsConstructor
@Service
public class StageStatisticsCalculator {

    private StageStatisticsRepository repository;
    private FrontStatisticsRepository frontStatisticsRepository;

    public StageStatisticsDto statisticsFor(StatisticsPeriod period, Stage stage) {
        List<StageStatistics> statistics = repository.findByPeriodAndStageDaily(period, stage);
        if (statistics.isEmpty()) {
            List<HourlyStatisticsDto> hourlyDto = new ArrayList<>();
            return new StageStatisticsDto(hourlyDto, stage, period);
        }
        return createDto(statistics, stage);
    }

    public void saveStageStatistics(StatisticsPeriod period, Stage stage, Front front) {
        StageStatistics statistics = repository.findByPeriodAndStageHourly(period, stage)
                .orElse(emptyStatistics(period, stage));
        statistics.addMeters(calculateMeters(front));
        repository.save(statistics);
    }

    private StageStatisticsDto createDto(List<StageStatistics> stageStatistics, Stage stage) {
        StageStatisticsDto dto = new StageStatisticsDto();
        dto.setStage(stage);
        List<HourlyStatisticsDto> hourlyDto = stageStatistics.stream().map(s -> new HourlyStatisticsDto(s.getMeters(), s.getPeriod().getHour())).collect(Collectors.toList());

        dto.setHourlyStatistics(hourlyDto);
        var date = stageStatistics.get(0).getPeriod();
        dto.setPeriod(new StatisticsPeriod(0, date.getDay(), date.getMonth(), date.getYear()));
        return dto;
    }

    private Double calculateMeters(Front front) {
        var frontEntity = frontStatisticsRepository.findByBarcode(front.getBarcode());
        return frontEntity
                .map(entity -> entity.getHeight() * entity.getWidth() / 1000000.0)
                .orElse(0.0);
    }
}
