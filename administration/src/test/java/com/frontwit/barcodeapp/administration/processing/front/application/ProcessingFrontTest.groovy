package com.frontwit.barcodeapp.administration.processing.front.application

import com.frontwit.barcodeapp.administration.processing.front.application.dto.ProcessFrontCommand
import com.frontwit.barcodeapp.administration.processing.front.model.*
import com.frontwit.barcodeapp.administration.processing.shared.Barcode
import com.frontwit.barcodeapp.administration.processing.shared.Stage
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvents
import spock.lang.Specification

class ProcessingFrontTest extends Specification implements SampleFront {

    DomainEvents domainEvents = Mock()
    FrontRepository frontRepository = Mock()
    ProcessingFront frontProcessing = new ProcessingFront(frontRepository, domainEvents)

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
            it.barcode == new Barcode(command.getBarcode())
            it.stage == Stage.valueOf(command.getStage())
        } as StageChanged)
    }

    def "should publish stage changed event when stage downgraded"() {
        given:
        persistedFront()
        and:
        def command = aDowngradeProcess()
        when:
        frontProcessing.process(command)
        then:
        1 * frontRepository.save(_)
        1 * domainEvents.publish({
            it.barcode == new Barcode(command.getBarcode())
            it.stage == Stage.valueOf(command.getStage())
        } as StageChanged)
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
            it.orderId == new Barcode(command.getBarcode()).getOrderId()
            it.delayedProcessFrontCommand == command
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
        return new ProcessFrontCommand(BARCODE.getBarcode(), Stage.MILLING.id, getIncrementedDateTime())
    }

    ProcessFrontCommand aUpgradeProcess() {
        return new ProcessFrontCommand(BARCODE.getBarcode(), Stage.BASE.id, getIncrementedDateTime())
    }

    ProcessFrontCommand aProcessWithSameStage() {
        return new ProcessFrontCommand(BARCODE.getBarcode(), Stage.POLISHING.id, getIncrementedDateTime())
    }

}
