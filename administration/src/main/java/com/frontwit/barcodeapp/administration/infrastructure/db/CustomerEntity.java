package com.frontwit.barcodeapp.administration.infrastructure.db;

import com.frontwit.barcodeapp.administration.processing.synchronization.SourceCustomer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
@Data
@NoArgsConstructor
public class CustomerEntity {

    @Id
    private Long id;
    private String name;
    private String address;
    private String route;

    public CustomerEntity(SourceCustomer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.address = customer.getAddress();
        this.route = customer.getRoute();
    }
}
