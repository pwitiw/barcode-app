package com.frontwit.barcodeapp.administration.order.processing.front.infrastructure.persistence;

import com.frontwit.barcodeapp.administration.order.processing.front.model.Front;
import com.frontwit.barcodeapp.administration.order.processing.front.model.ProcessingDetails;
import com.frontwit.barcodeapp.administration.order.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.order.processing.synchronization.TargetFront;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "front")
class FrontEntity {

    @Id
    private long barcode;
    private int height;
    private int width;
    private long quantity;
    private Stage currentStage = Stage.INIT;
    private String comment;
    private Set<ProcessingDetails> processings = new HashSet<>();
    private Set<ProcessingDetails> amendments = new HashSet<>();

    FrontEntity(TargetFront targetFront) {
        this.barcode = targetFront.getBarcode().getBarcode();
        this.comment = targetFront.getComment();
        this.width = targetFront.getDimensions().getWidth();
        this.height = targetFront.getDimensions().getHeight();
    }

    void update(Front front) {
        currentStage = front.getCurrentStage();
        processings = front.getProcessings();
        amendments = front.getAmendments();
    }
}
