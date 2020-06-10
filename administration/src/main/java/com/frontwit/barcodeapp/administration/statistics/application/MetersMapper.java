package com.frontwit.barcodeapp.administration.statistics.application;

import com.frontwit.barcodeapp.administration.processing.synchronization.TargetFront;
import com.frontwit.barcodeapp.administration.statistics.domain.order.Meters;

import java.util.List;

public class MetersMapper {

    static Meters map(List<TargetFront> fronts) {
        var result = fronts.stream()
                .map(front -> front.getDimensions().getHeight() * front.getDimensions().getWidth() * front.getQuantity().getValue())
                .reduce(0, (a, b) -> a + b)
                .doubleValue();
        return Meters.of(result);
    }
}
