package com.frontwit.barcodeapp.administration.catalogue;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class OrderCommand {

    private MongoTemplate mongoTemplate;


}
