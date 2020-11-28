package com.frontwit.barcodeapp.administration.processing.synchronization


import com.frontwit.barcodeapp.administration.statistics.domain.order.Meters
import spock.lang.Specification

import static com.frontwit.barcodeapp.administration.CommonFixtures.aBarcode
import static SynchronizationFixtures.aTargetFront

class MetersCalculatorTest extends Specification {
    def "should calculate meters out of fronts "() {
        given:
        def fronts = [
                aTargetFront(aBarcode(), 2, 1_000, 1_000),
                aTargetFront(aBarcode(), 2, 2_000, 2_000)
        ]
        when:
        def result = MetersCalculator.calculate(fronts)

        then:
        result == Meters.of(10.0)
    }

    def "empty list of fronts gives 0 meters"() {
        given:
        def emptyFronts = []
        when:
        def result = MetersCalculator.calculate(emptyFronts)

        then:
        result == Meters.ZERO
    }
}
