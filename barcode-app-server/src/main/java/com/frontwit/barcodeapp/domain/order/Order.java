package com.frontwit.barcodeapp.domain.order;

import com.frontwit.barcodeapp.domain.order.dto.OrderDetailDto;
import com.frontwit.barcodeapp.domain.order.dto.OrderDto;
import com.frontwit.barcodeapp.domain.order.dto.ProcessCommand;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "order")
@Builder
public class Order {

    @Getter
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Set<Component> components = new HashSet<>();
    @CreatedDate
    private LocalDate orderedAt;
    private String color;
    private String size;
    @Getter
    private Stage stage;
    private String cutter;
    private String comment;
    private String customer;
    @Getter
    private boolean isComplete;

    void update(ProcessCommand process) {
        stage = Stage.MILLING;
        components
                .stream()
                .filter(component -> component.isApplicable(process))
                .forEach(component -> component.apply(process));
        updateCompleteness();
    }

    OrderDto dto() {
        return null;
    }

    OrderDetailDto detailDto() {
        return OrderDetailDto
                .builder()
                .id(id)
                .color(color)
                .comment(comment)
                .customer(customer)
                .stage(String.valueOf(stage))
                .orderedAt(orderedAt)
                .build();
    }

    private void updateCompleteness() {
        isComplete = components.stream().allMatch(Component::isComplete);
    }
}
