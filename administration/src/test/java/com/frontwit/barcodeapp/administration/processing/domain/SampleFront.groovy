package com.frontwit.barcodeapp.administration.processing.domain

import java.time.LocalDateTime

trait SampleFront {

    def BARCODE = new Barcode(101L)

    Front aSingleFrontWithAppliedProcesses(Status... statuses) {
        List<ProcessFront> processes = statuses.each { status -> aProcessFront(status) }
        return Front.recreateFrom(UUID.randomUUID(), BARCODE, 1, processes);
    }

    Front aNotProcessedSingleFront() {
        return
    }

    FrontProcessed frontProcessed() {
        
    }


    ProcessFront aProcessFront(Status status) {
        new ProcessFront(BARCODE, status, LocalDateTime.now())
    }
}
