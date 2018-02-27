package com.frontwit.barcodeapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private Set<Component> components = new HashSet<>();

    @CreatedDate
    private LocalDate orderDate;
}
