package com.frontwit.barcodeapp.administration.processing.order.infrastructure;

import com.frontwit.barcodeapp.administration.catalogue.dto.FrontDto;
import com.frontwit.barcodeapp.administration.catalogue.dto.OrderDetailDto;
import com.frontwit.barcodeapp.administration.catalogue.dto.OrderDto;
import com.frontwit.barcodeapp.administration.processing.order.model.Order;
import com.frontwit.barcodeapp.administration.processing.order.model.UpdateStagePolicy;
import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.processing.shared.Quantity;
import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import com.frontwit.barcodeapp.administration.processing.synchronization.TargetFront;
import com.frontwit.barcodeapp.administration.processing.synchronization.TargetOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Document(collection = "order")
@Data
@NoArgsConstructor
public class OrderEntity {

    @Id
    private Long id;
    @NotNull
    private String name;
    private LocalDate orderedAt;
    private String color;
    private String size;
    private Stage stage;
    private String cutter;
    private String comment;
    private String customer;
    private String route;
    private boolean completed;
    private int quantity;
    private LocalDate lastProcessedOn;
    private boolean packed;
    private Set<Barcode> notPackedFronts;

    OrderEntity(TargetOrder targetOrder) {
        this.id = targetOrder.getOrderId().getOrderId();
        this.name = targetOrder.getInfo().getName();
        this.color = targetOrder.getInfo().getColor();
        this.cutter = targetOrder.getInfo().getCutter();
        this.size = targetOrder.getInfo().getSize();
        this.stage = Stage.INIT;
        this.orderedAt = targetOrder.getInfo().getOrderedAt();
        this.customer = targetOrder.getInfo().getCustomer();
        this.comment = targetOrder.getComment().compose();
        this.route = targetOrder.getInfo().getRoute();
        this.quantity = targetOrder.getFronts().stream()
                .map(TargetFront::getQuantity)
                .map(Quantity::getValue)
                .mapToInt(Integer::intValue).sum();
        this.notPackedFronts = targetOrder.getFronts().stream()
                .map(TargetFront::getBarcode)
                .collect(Collectors.toSet());
        this.completed = false;
        this.packed = false;
    }

    void update(Order order) {
        this.stage = order.getStage();
        this.packed = order.isPacked();
        this.notPackedFronts = order.getNotPackedFronts();
        this.lastProcessedOn = order.getLastProcessedOn();
    }

    Order toDomainModel(UpdateStagePolicy policy) {
        return new Order(new OrderId(id), notPackedFronts, stage, lastProcessedOn, completed, policy);
    }

    public OrderDetailDto detailsDto(List<FrontDto> fronts) {
        return new OrderDetailDto(id, name, color, size, cutter, comment, customer, route, stage, orderedAt, fronts, completed, packed);
    }

    public OrderDto dto() {
        return new OrderDto(id, name, orderedAt, stage, quantity, customer, route, packed);
    }
}
