package com.frontwit.barcodeapp.administration.processing.front.model

import com.frontwit.barcodeapp.administration.processing.shared.Stage
import spock.lang.Specification
import spock.lang.Unroll

import static com.frontwit.barcodeapp.administration.processing.shared.Stage.*
import static java.time.LocalDateTime.now

class FrontProcessorScenarios extends Specification implements SampleFront {

    @Unroll
    def "should apply front processing #nextStage and update status"() {
        given:
        def front = aFrontWithAppliedProcesses(1, processedStages as Stage[])

        when:
        def events = front.apply(new ProcessingDetails(nextStage, getIncrementedDateTime()))

        then:
        events.size() == amount

        where:
        processedStages                                | amount | nextStage
        []                                             | 1      | MILLING
        [MILLING]                                      | 2      | POLISHING
        [MILLING, POLISHING]                           | 3      | BASE
        [MILLING, POLISHING, BASE]                     | 4      | GRINDING
        [MILLING, POLISHING, BASE, GRINDING]           | 5      | PAINTING
        [MILLING, POLISHING, BASE, GRINDING, PAINTING] | 7      | PACKING
    }

    def "stage is updated when at least one processed"() {
        given:
        def front = aFrontWithAppliedProcesses(2)

        when:
        def events = front.apply(new ProcessingDetails(MILLING, now()))

        then:
        !events.isEmpty()
        MILLING == events.get(0).stage
    }

    def "stage is not downgraded when amendment applied"() {
        given:
        def front = aFrontWithAppliedProcesses(1, MILLING, POLISHING)

        when:
        def events = front.apply(new ProcessingDetails(MILLING, getIncrementedDateTime()))

        then:
        events.size() == 2
    }

    def "amending on the same stage as current does not update stage"() {
        given:
        def front = aFrontWithAppliedProcesses(1, MILLING, POLISHING)

        when:
        def events = front.apply(new ProcessingDetails(POLISHING, getIncrementedDateTime()))

        then:
        events.size() == 2
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
        def front = aFront(BARCODE, 1);

        when:
        def events = front.apply(new ProcessingDetails(PACKING, now()))

        then:
        events.size() == 2
        events.containsAll(new FrontPacked(BARCODE), new StageChanged(BARCODE, PACKING))
    }

    def "do not raise event when not all fronts packed"() {
        def front = aFront(BARCODE, 2);

        when:
        def events = front.apply(new ProcessingDetails(PACKING, now()))

        then:
        events.size() == 1
        events.containsAll( new StageChanged(BARCODE, PACKING))
    }
}
