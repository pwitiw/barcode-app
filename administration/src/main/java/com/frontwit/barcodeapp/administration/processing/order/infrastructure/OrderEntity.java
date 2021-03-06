package com.frontwit.barcodeapp.administration.processing.order.infrastructure;

import com.frontwit.barcodeapp.administration.catalogue.orders.dto.*;
import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerEntity;
import com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity;
import com.frontwit.barcodeapp.administration.processing.order.model.Order;
import com.frontwit.barcodeapp.administration.processing.order.model.OrderType;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.ZoneId.of;

@Document(collection = "order")
@Data
@NoArgsConstructor
@SuppressWarnings({"ClassFanOutComplexity", "PMD.TooManyFields"})
public class OrderEntity {
    private static final ZoneId CLIENT_ZONE_ID = of("Europe/Paris");

    @Id
    private Long id;
    @NotNull
    private String name;
    private Instant orderedAt;
    private String color;
    private String size;
    private Stage stage;
    private String cutter;
    private String comment;
    private Long customerId;
    private boolean completed;
    private int quantity;
    private Instant lastProcessedOn;
    private Instant deadline;
    private boolean packed;
    private Set<Barcode> notPackedFronts;
    private BigDecimal valuation;
    private OrderType type;

    OrderEntity(TargetOrder targetOrder) {
        this.id = targetOrder.getOrderId().getId();
        this.name = targetOrder.getInfo().getName();
        this.color = targetOrder.getInfo().getColor();
        this.cutter = targetOrder.getInfo().getCutter();
        this.size = targetOrder.getInfo().getSize();
        this.stage = Stage.INIT;
        this.orderedAt = targetOrder.getInfo().getOrderedAt();
        this.customerId = targetOrder.getCustomerId();
        this.comment = targetOrder.getComment().compose();
        this.quantity = targetOrder.getFronts().stream()
                .map(TargetFront::getQuantity)
                .map(Quantity::getValue)
                .mapToInt(Integer::intValue).sum();
        this.notPackedFronts = targetOrder.getFronts().stream()
                .map(TargetFront::getBarcode)
                .collect(Collectors.toSet());
        this.completed = false;
        this.packed = false;
        this.type = targetOrder.getInfo().getType();
        this.deadline = targetOrder.getDeadline();
        this.valuation = targetOrder.getValuation();
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

    public OrderDetailDto detailsDto(List<FrontEntity> fronts, CustomerEntity customer) {
        var deadline = this.deadline != null ? this.deadline.toEpochMilli() : null;
        var customerName = customer.getName();
        var route = customer.getRoute();
        var frontDtos = fronts.stream()
                .map(FrontEntity::dto)
                .collect(Collectors.toList());
        return new OrderDetailDto(id, name, color, size, cutter, comment, customerName, route, stage,
                LocalDate.ofInstant(orderedAt, CLIENT_ZONE_ID), frontDtos, completed, packed, deadline, valuation, type);
    }

    public OrderDto dto(CustomerEntity customer) {
        LocalDate zonedProcessedOnDate = this.lastProcessedOn != null
                ? LocalDate.ofInstant(this.lastProcessedOn, CLIENT_ZONE_ID)
                : null;
        LocalDate zonedOrderedAt = this.orderedAt != null ? LocalDate.ofInstant(this.orderedAt, CLIENT_ZONE_ID) : null;
        return new OrderDto(
                id,
                name,
                zonedOrderedAt,
                zonedProcessedOnDate,
                stage,
                quantity,
                customer.getName(),
                customer.getRoute(),
                packed,
                completed,
                type
        );
    }

    public ReminderDto reminderDto(CustomerEntity customer) {
        return new ReminderDto(name, customer.getName(), deadline.toEpochMilli());
    }


    public OrderInfoDto deliveryOrderDto() {
        double valuation = Optional.ofNullable(this.valuation)
                .orElse(BigDecimal.valueOf(0d))
                .doubleValue();
        LocalDate zonedOrderedAt = getOrderedAt() != null ? LocalDate.ofInstant(getOrderedAt(), CLIENT_ZONE_ID) : null;

        return new OrderInfoDto(id, name, quantity, valuation, zonedOrderedAt, cutter, color);
    }
}
