package com.frontwit.barcodeapp.administration.processing.domain

import spock.lang.Specification

import static com.frontwit.barcodeapp.administration.processing.domain.Status.MILLING
import static com.frontwit.barcodeapp.administration.processing.domain.Status.POLISHING


class FrontTest extends Specification implements SampleFront {

    def "should apply front processing"() {
        given:
        def front = aSingleFrontWithAppliedProcesses()

        when:
        front.apply(aProcessFront(MILLING), ProcessingPolicy.ALWAYS_ALLOW)

        then:
        front.getPendingEvents().size() == 1
        front.getStatus() == status

        where:
        status                                           | pendingEvents
        [MILLING]                            | 1
        [MILLING, POLISHING]                 | 2
//        newArrayList(MILLING, POLISHING, BASE)           | 3
//        newArrayList(MILLING, POLISHING, BASE, GRINDING) | 4
//        PAINTING                                         | 5
//        PACKING                                          | 6
//        IN_DELIVERY                                      | 7
    }

//    def "should apply front processing"() {
//        given:
//        def front = aNotProcessedSingleFront()
//
//        when:
//        front.apply(aProcessFront(Status.MILLING), ProcessingPolicy.ALWAYS_ALLOW)
//
//        then:
//        front.getPendingEvents().size() == 1
//        front.getStatus() == Status.MILLING
//    }

}
