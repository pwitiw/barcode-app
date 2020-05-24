package com.frontwit.barcodeapp.administration.infrastructure.db;

import com.frontwit.barcodeapp.administration.processing.synchronization.TargetOrder;
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

    public CustomerEntity(TargetOrder.Customer customer) {
        this.id = customer.getCustomerId();
        this.name = customer.getName();
        this.address = customer.getAddress();
        this.route = customer.getRoute();
    }
}
