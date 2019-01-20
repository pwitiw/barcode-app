package com.frontwit.barcodeapp.model;

import com.frontwit.barcodeapp.datatype.Stage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
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
    private Long barcode;
    @NotNull
    private String name;
    @NotNull
    private Set<Component> components = new HashSet<>();
    @CreatedDate
    private LocalDate orderedAt;
    @DBRef
    private Route route;

    private Stage stage;

    private String color;

    private String size;

    private String cutter;

    private String comment;

    private String customer;
    // ensure uniqueness
    private Long extId;
}
