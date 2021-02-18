package com.frontwit.barcodeapp.administration.processing.front.application

import com.frontwit.barcodeapp.administration.processing.front.application.dto.ProcessFrontCommand
import com.frontwit.barcodeapp.administration.processing.front.model.*
import com.frontwit.barcodeapp.administration.processing.shared.Stage
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvents
import spock.lang.Specification

class FrontProcessorTest extends Specification implements SampleFront {

    DomainEvents domainEvents = Mock()
    FrontRepository frontRepository = Mock()
    FrontProcessor frontProcessing = new FrontProcessor(frontRepository, domainEvents)

    def "should publish stage changed event when stage updated"() {
        given:
        persistedFront()
        and:
        def command = aUpgradeProcess()
        when:
        frontProcessing.process(command)
        then:
        1 * frontRepository.save(_)
        1 * domainEvents.publish({
            it.barcode == command.getBarcode()
            it.stage == Stage.valueOf(command.getStage())
        } as FrontStageChanged)
    }

    def "should not fire any event when stage not changed"() {
        given:
        persistedFront()
        and:
        def command = aProcessWithSameStage()
        when:
        frontProcessing.process(command)
        then:
        1 * frontRepository.save(_)
        0 * domainEvents.publish()
    }

    def "should fire event when front not found"() {
        given:
        frontNotPersisted()
        and:
        def command = aProcessWithSameStage()
        when:
        frontProcessing.process(command)
        then:
        1 * domainEvents.publish({
            it.orderId == command.getBarcode().getOrderId()
            it.stage == command.getStage()
            it.dateTime == command.getDateTime()
        } as FrontNotFound)
    }

    def frontIsPersisted() {
        Front front = aFrontWithAppliedProcesses(1, Stage.MILLING, Stage.POLISHING)
        frontRepository.findBy(BARCODE) >> Optional.of(front)
    }

    def frontNotPersisted() {
        frontRepository.findBy(BARCODE) >> Optional.empty()
    }

    def persistedFront() {
        Front front = aFrontWithAppliedProcesses(1, Stage.MILLING, Stage.POLISHING)
        frontRepository.findBy(BARCODE) >> Optional.of(front)
    }

    ProcessFrontCommand aDowngradeProcess() {
        return new ProcessFrontCommand(BARCODE, Stage.MILLING.id, getIncrementedDateTime())
    }

    ProcessFrontCommand aUpgradeProcess() {
        return new ProcessFrontCommand(BARCODE, Stage.BASE.id, getIncrementedDateTime())
    }

    ProcessFrontCommand aProcessWithSameStage() {
        return new ProcessFrontCommand(BARCODE, Stage.POLISHING.id, getIncrementedDateTime())
    }

}
