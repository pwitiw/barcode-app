package com.frontwit.barcodeapp.model;

import com.frontwit.barcodeapp.datatype.Stage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order")
public class Order {

    @Id
    ObjectId id;

    @NotEmpty
    private String name;

    @NotEmpty
    private Set<Component> components = new HashSet<>();

    @CreatedDate
    private LocalDate orderedAt;

    @DBRef
    private Route route;

    private Integer componentAmount;

    private Integer damagedComponentsAmount;

    private Stage stage;
}
