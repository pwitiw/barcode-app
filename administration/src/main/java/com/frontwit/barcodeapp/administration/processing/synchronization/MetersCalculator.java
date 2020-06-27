package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.statistics.domain.order.Meters;

import java.util.List;

public class MetersCalculator {
    public static Meters calculate(List<TargetFront> fronts) {
        var result = fronts.stream()
                .map(MetersCalculator::computeFrontArea)
                .reduce(0, Integer::sum)
                .doubleValue();
        return Meters.of(result);
    }

    private static Integer computeFrontArea(TargetFront front) {
        return front.getDimensions().getHeight() * front.getDimensions().getWidth() * front.getQuantity().getValue();
    }
}
