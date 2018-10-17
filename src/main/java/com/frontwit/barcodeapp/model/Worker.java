package com.frontwit.barcodeapp.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "worker")
public class Worker {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private Long barcode;

    private String firstName;

    private String lastName;

}
