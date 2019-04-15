package com.frontwit.barcodeapp.domain.order.processing;

import com.frontwit.barcodeapp.domain.order.processing.dto.ProcessCommand;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "order")
public class Order {

    @Id
    @Getter
    private ObjectId id;
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
    private Long extId;
    @Getter
    private boolean isComplete;

    Order(Long extId, String name, Set<Component> components) {
        this.id = new ObjectId();
        this.name = name;
        this.extId = extId;
        this.components = components;
    }

    void update(ProcessCommand process) {
        stage = Stage.MILLING;
        components
                .stream()
                .filter(component -> component.isApplicable(process))
                .forEach(component -> component.apply(process));
        updateCompleteness();
    }

    private void updateCompleteness() {
        isComplete = components.stream().allMatch(Component::isComplete);
    }
}
