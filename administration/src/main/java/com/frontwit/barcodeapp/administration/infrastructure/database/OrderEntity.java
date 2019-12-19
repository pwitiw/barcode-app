package com.frontwit.barcodeapp.administration.infrastructure.database;

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
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    private boolean completed;
    private Map<Long, Stage> fronts;
    private int quantity;

    public OrderEntity(TargetOrder targetOrder) {
        this.id = targetOrder.getOrderId().getOrderId();
        this.name = targetOrder.getInfo().getName();
        this.color = targetOrder.getInfo().getColor();
        this.cutter = targetOrder.getInfo().getCutter();
        this.size = targetOrder.getInfo().getSize();
        this.stage = Stage.INIT;
        this.orderedAt = targetOrder.getInfo().getOrderedAt();
        this.customer = targetOrder.getInfo().getCustomer();
        this.comment = targetOrder.getComment().compose();
        this.quantity = targetOrder.getFronts().stream()
                .map(TargetFront::getQuantity)
                .map(Quantity::getValue)
                .mapToInt(Integer::intValue).sum();
        this.fronts = targetOrder.getFronts().stream()
                .map(front -> front.getBarcode().getBarcode())
                .collect(Collectors.toMap(Function.identity(), t -> Stage.INIT));
    }

    public void update(Order order) {
        this.stage = order.getStage();
        this.completed = order.isCompleted();
        order.getFronts().forEach((barcode, stage) -> fronts.put(barcode.getBarcode(), stage));
    }

    public Order toDomainModel(UpdateStagePolicy policy) {
        var domainFronts = new HashMap<Barcode, Stage>();
        fronts.forEach((key, value) -> domainFronts.put(new Barcode(key), value));
        return new Order(new OrderId(id), domainFronts, stage, completed, policy);
    }

    public OrderDetailDto detailsDto(List<FrontDto> fronts) {
        return new OrderDetailDto(id, name, color, size, cutter, comment, customer, stage, orderedAt, fronts, completed);
    }

    public OrderDto dto() {
        return new OrderDto(id, name, orderedAt, stage, quantity, customer);
    }
}
