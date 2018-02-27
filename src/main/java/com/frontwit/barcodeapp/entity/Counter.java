package com.frontwit.barcodeapp.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document
@Data
public class Counter {

    @Id
    String id;

    @NotNull
    Long value;
}
