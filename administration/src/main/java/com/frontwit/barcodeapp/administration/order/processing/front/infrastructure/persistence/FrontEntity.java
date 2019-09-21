package com.frontwit.barcodeapp.administration.order.processing.front.infrastructure.persistence;

import com.frontwit.barcodeapp.administration.order.processing.front.model.Front;
import com.frontwit.barcodeapp.administration.order.processing.front.model.FrontProcessingPolicy;
import com.frontwit.barcodeapp.administration.order.processing.front.model.ProcessingDetails;
import com.frontwit.barcodeapp.administration.order.processing.order.application.dto.FrontDto;
import com.frontwit.barcodeapp.administration.order.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.order.processing.shared.Quantity;
import com.frontwit.barcodeapp.administration.order.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.order.processing.synchronization.TargetFront;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "front")
@Data
@NoArgsConstructor
public class FrontEntity {

    @Id
    private long barcode;
    private long orderId;
    private int height;
    private int width;
    private int quantity;
    private Stage stage = Stage.INIT;
    private String comment;
    private Set<ProcessingDetails> processings = new HashSet<>();
    private Set<ProcessingDetails> amendments = new HashSet<>();

    FrontEntity(TargetFront targetFront) {
        this.barcode = targetFront.getBarcode().getBarcode();
        this.orderId = targetFront.getOrderId().getOrderId();
        this.comment = targetFront.getComment();
        this.width = targetFront.getDimensions().getWidth();
        this.height = targetFront.getDimensions().getHeight();
        this.quantity = targetFront.getQuantity().getValue();
    }

    void update(Front front) {
        stage = front.getCurrentStage();
        processings = front.getProcessings();
        amendments = front.getAmendments();
    }

    public FrontDto dto() {
        return new FrontDto(barcode, height, width, quantity, stage, comment, processings, amendments);

    }

    Front toDomainModel(FrontProcessingPolicy policy) {
        return new Front(new Barcode(barcode), new Quantity(quantity), stage, processings, amendments, policy);
    }
}
