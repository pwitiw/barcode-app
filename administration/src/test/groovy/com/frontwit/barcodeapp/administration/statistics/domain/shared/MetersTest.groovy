package com.frontwit.barcodeapp.administration.statistics.domain.shared

import com.frontwit.barcodeapp.administration.statistics.domain.order.Meters
import spock.lang.Specification

class MetersTest extends Specification {

    def "should add meters"() {
        when:
        def result = Meters.of(1).plus(Meters.of(1))
        then:
        result == Meters.of(2)
    }
}
