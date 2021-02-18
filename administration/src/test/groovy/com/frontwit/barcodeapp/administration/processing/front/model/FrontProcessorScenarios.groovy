package com.frontwit.barcodeapp.administration.processing.front.model

import com.frontwit.barcodeapp.administration.processing.shared.Quantity
import com.frontwit.barcodeapp.administration.processing.shared.Stage
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvent
import spock.lang.Specification
import spock.lang.Unroll

import static com.frontwit.barcodeapp.administration.processing.shared.Stage.*
import static java.time.LocalDateTime.now

class FrontProcessorScenarios extends Specification implements SampleFront {

    @Unroll
    def "should apply front processing #nextStage and update status"() {
        given:
        def processings = processedStages.size() + 1
        def front = aFrontWithAppliedProcesses(1, processedStages as Stage[])

        when:
        def events = front.apply(new ProcessingDetails(nextStage, getIncrementedDateTime()))

        then:
        containsEvent(events, FrontProcessed, processings)
        containsEvent(events, FrontStageChanged, processings)

        where:
        processedStages                                | nextStage
        []                                             | MILLING
        [MILLING]                                      | POLISHING
        [MILLING, POLISHING]                           | BASE
        [MILLING, POLISHING, BASE]                     | GRINDING
        [MILLING, POLISHING, BASE, GRINDING]           | PAINTING
        [MILLING, POLISHING, BASE, GRINDING, PAINTING] | PACKING
    }

    def "should amend if all fronts processed on given stage"() {
        given:
        def front = aFrontWithAppliedProcesses(1, MILLING, POLISHING)
        when:
        def events = front.apply(new ProcessingDetails(MILLING, getIncrementedDateTime()))

        then:
        containsEvent(events, FrontAmended)
    }

    def "amending on the same stage as current does not update stage"() {
        given:
        def front = aFrontWithAppliedProcesses(1, MILLING, POLISHING)

        when:
        def events = front.apply(new ProcessingDetails(POLISHING, getIncrementedDateTime()))

        then:
        containsEvent(events, FrontProcessed, 2)
        containsEvent(events, FrontAmended)
    }

    def "can not process same front with frequency greater than once per 3 seconds"() {
        given:
        def dateTime = now()
        def front = aFrontWithAppliedProcess(new ProcessingDetails(MILLING, dateTime))

        when:
        front.apply(new ProcessingDetails(MILLING, getIncrementedDateTime(3)))

        then:
        thrown(ProcessingPolicyViolationException)
    }

    @Unroll
    def "can not process front on stage #stage which is completed"() {
        def front = aFrontWithCompletedProcessing()

        when:
        front.apply(new ProcessingDetails(stage, now()))

        then:
        thrown(ProcessingPolicyViolationException)

        where:
        stage       | _
        MILLING     | _
        POLISHING   | _
        BASE        | _
        GRINDING    | _
        PAINTING    | _
        PACKING     | _
        IN_DELIVERY | _
    }

    def "raise event when all fronts packed"() {
        def quantity = new Quantity(1)
        def front = aFront(BARCODE, quantity.getValue())

        when:
        def events = front.apply(new ProcessingDetails(PACKING, now()))

        then:
        containsEvent(events, FrontPacked)
    }

    def "do not raise event when not all fronts packed"() {
        def front = aFront(BARCODE, 2);

        when:
        def events = front.apply(new ProcessingDetails(PACKING, now()))

        then:
        events.containsAll(new FrontStageChanged(BARCODE, PACKING))
    }

    private static boolean containsEvent(List<DomainEvent> allEvents, Class<? extends DomainEvent> clazz, amount = 1) {
        def result = allEvents.findAll { it -> it.getClass() == clazz }
        result.size() == amount
    }
}
