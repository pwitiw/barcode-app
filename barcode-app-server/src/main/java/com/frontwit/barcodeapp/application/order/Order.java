package com.frontwit.barcodeapp.application.order;

import com.frontwit.barcodeapp.application.order.dto.ComponentDto;
import com.frontwit.barcodeapp.application.order.dto.OrderDetailDto;
import com.frontwit.barcodeapp.application.order.dto.ProcessCommand;
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

import static java.lang.String.format;

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
    private Set<Component> components = new HashSet<>();
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
    private Boolean isComplete;

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
        isComplete = components.stream().allMatch(Component::isComplete);
    }
}
