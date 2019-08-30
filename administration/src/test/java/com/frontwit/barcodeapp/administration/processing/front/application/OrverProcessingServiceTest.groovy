//package com.frontwit.barcodeapp.administration.processing.front.application
//
//import com.frontwit.barcodeapp.administration.application.order.dto.ProcessFrontDto
//import com.frontwit.barcodeapp.administration.processing.front.application.event.FrontProcessed
//import com.frontwit.barcodeapp.administration.processing.front.infrastructure.ProcessingEventDispatcher
//import spock.lang.Specification
//
//import java.time.LocalDateTime
//
//class OrverProcessingServiceTest extends Specification {
//
//    FrontProcessingService service
//    ProcessingEventDispatcher eventDispatcher = Mock(ProcessingEventDispatcher)
//    ProcessingRepository processingRepository = Mock(ProcessingRepository)
//
//    def "should save and propagate front processed event"() {
//
//        when:
//        service.apply(new ProcessFrontDto(1))
//
//        then:
//        1 * eventDispatcher.emit(frontProcessed())
//    }
//
//    FrontProcessed frontProcessed(barcode, currentStage) {
//        return new FrontProcessed(barcode, currentStage)
//
//    }
//}
