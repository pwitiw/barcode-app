package com.frontwit.barcodeapp.entity;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Order {

    @Id
    @NotEmpty
    @Indexed(unique = true)
    @Field("id")
    private Long id;

    private String name;

    private List<Component> components;

    private LocalDate orderDate;
}
