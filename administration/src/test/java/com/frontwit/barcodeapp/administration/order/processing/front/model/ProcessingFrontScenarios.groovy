package com.frontwit.barcodeapp.administration.order.processing.front.model


import com.frontwit.barcodeapp.administration.order.processing.shared.Stage
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

import static com.frontwit.barcodeapp.administration.order.processing.shared.Stage.*
import static java.time.LocalDateTime.now

class ProcessingFrontScenarios extends Specification implements SampleFront {

    @Unroll
    def "should apply front processing #nextStage and update status"() {
        given:
        def front = aFrontWithAppliedProcesses(1, processedStages as Stage[])

        when:
        def event = front.apply(new ProcessingDetails(nextStage, getIncrementedDateTime()))

        then:
        event.isPresent()
        nextStage == event.get().stage

        where:
        processedStages                                         | nextStage
        []                                                      | MILLING
        [MILLING]                                               | POLISHING
        [MILLING, POLISHING]                                    | BASE
        [MILLING, POLISHING, BASE]                              | GRINDING
        [MILLING, POLISHING, BASE, GRINDING]                    | PAINTING
        [MILLING, POLISHING, BASE, GRINDING, PAINTING]          | PACKING
        [MILLING, POLISHING, BASE, GRINDING, PAINTING, PACKING] | IN_DELIVERY
    }

    def "stage is updated when at least one processed"() {
        given:
        def front = aFrontWithAppliedProcesses(2)

        when:
        def event = front.apply(new ProcessingDetails(MILLING, now()))

        then:
        event.isPresent()
        MILLING == event.get().stage
    }

    def "stage is downgraded when amendment applied"() {
        given:
        def front = aFrontWithAppliedProcesses(1, MILLING, POLISHING)

        when:
        def event = front.apply(new ProcessingDetails(MILLING, getIncrementedDateTime()))

        then:
        event.isPresent()
        MILLING == event.get().stage
    }

    def "amending on the same stage as current does not update stage"() {
        given:
        def front = aFrontWithAppliedProcesses(1, MILLING, POLISHING)

        when:
        def event = front.apply(new ProcessingDetails(POLISHING, getIncrementedDateTime()))

        then:
        event.isEmpty()
    }

    @Unroll
    def "amendment at stage #stage downgrade stage to #stage"() {
        given:
        def front = aFrontWithAppliedProcesses(1, MILLING, POLISHING, BASE, GRINDING, PAINTING, PACKING)

        when:
        def event = front.apply(new ProcessingDetails(stage, getIncrementedDateTime()))

        then:
        event.isPresent()
        stage == event.get().stage

        where:
        stage     | _
        PAINTING  | _
        GRINDING  | _
        BASE      | _
        POLISHING | _
        MILLING   | _
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

    @Unroll
    @Ignore
    def "can not process front in wrong order"() {
        when:
        aFrontWithAppliedProcesses(1, stages as Stage[])

        then:
        thrown(ProcessingPolicyViolationException)

        true
        where:
        stages                                        | _
        [POLISHING]                                   | _
        [BASE]                                        | _
        [GRINDING]                                    | _
        [PAINTING]                                    | _
        [PACKING]                                     | _
        [IN_DELIVERY]                                 | _
        [MILLING, BASE]                               | _
        [MILLING, POLISHING, GRINDING]                | _
        [MILLING, POLISHING, BASE, PAINTING]          | _
        [MILLING, POLISHING, BASE, GRINDING, PACKING] | _
    }
}
