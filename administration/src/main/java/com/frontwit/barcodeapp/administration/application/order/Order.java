package com.frontwit.barcodeapp.administration.application.order;

import com.frontwit.barcodeapp.administration.application.order.dto.ComponentDto;
import com.frontwit.barcodeapp.administration.application.order.dto.OrderDetailDto;
import com.frontwit.barcodeapp.administration.application.order.dto.OrderDto;
import com.frontwit.barcodeapp.administration.application.order.dto.ProcessCommand;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Document(collection = "order")
@Builder
@ToString(of = {"id", "stage"})
public class Order {

    @Getter
    @Id
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Set<Component> components ;
    @CreatedDate
    private LocalDate orderedAt;
    private String color;
    private String size;
    @Getter(value = AccessLevel.PACKAGE)
    private Stage stage;
    private String cutter;
    private String comment;
    private String customer;
    @Getter(value = AccessLevel.PACKAGE)
    private Boolean isCompleted;

    void update(ProcessCommand process) {
        stage = Stage.MILLING;
        components
                .stream()
                .filter(component -> component.isApplicable(process))
                .forEach(component -> component.apply(process));
        updateCompleteness();
    }

    OrderDetailDto detailDto() {
        List<ComponentDto> componentDtos = components
                .stream()
                .map(Component::dto)
                .collect(Collectors.toList());
        return OrderDetailDto.builder()
                .id(id)
                .name(name)
                .color(color)
                .size(size)
                .cutter(cutter)
                .customer(customer)
                .stage(stage.name())
                .comment(comment)
                .orderedAt(orderedAt)
                .components(componentDtos)
                .build();
    }

    OrderDto dto() {
        return OrderDto.builder()
                .id(id)
                .cutter(cutter)
                .color(color)
                .damagedQuantity(0) // TODO adjust
                .name(name)
                .orderedAt(orderedAt)
                .quantity(components.size())
                .build();
    }

    // TODO componenty
    static Order valueOf(OrderDetailDto dto) {
        return Order.builder()
                .id(dto.getId())
                .customer(dto.getCustomer())
                .name(dto.getName())
                .size(dto.getSize())
                .comment(dto.getComment())
                .cutter(dto.getCutter())
                .stage(Stage.MILLING)
                .components(new HashSet<>())
                .build();
    }

    private void updateCompleteness() {
        isCompleted = components.stream().allMatch(Component::isComplete);
    }
}
