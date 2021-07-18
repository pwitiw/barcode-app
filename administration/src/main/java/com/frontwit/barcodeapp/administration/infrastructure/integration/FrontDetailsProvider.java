package com.frontwit.barcodeapp.administration.infrastructure.integration;

import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerEntity;
import com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity;
import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity;
import com.frontwit.barcodeapp.api.integration.PrintableLabelEvent;
import com.frontwit.barcodeapp.api.shared.Barcode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FrontDetailsProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrontDetailsProvider.class.getName());
    private final MongoTemplate mongoTemplate;

    public Optional<PrintableLabelEvent> find(Barcode barcode) {
        var orderId = barcode.getOrderId().getId();
        var order = mongoTemplate.findById(orderId, OrderEntity.class);
        var front = mongoTemplate.findById(barcode.getBarcode(), FrontEntity.class);
        LOGGER.debug("orderId: {}, barcode: {}, order: {}, front: {} ",
                orderId,
                barcode.getBarcode(),
                valueOrNullString(order),
                valueOrNullString(front)
        );
        if (order == null || front == null) {
            return Optional.empty();
        }
        var customer = mongoTemplate.findById(order.getCustomerId(), CustomerEntity.class);

        return Optional.of(printableLabel(order, front, customer));
    }

    private static PrintableLabelEvent printableLabel(OrderEntity order, FrontEntity front, CustomerEntity customer) {
        return new PrintableLabelEvent(
                order.getId(),
                order.getName(),
                front.getStage(),
                order.getQuantity(),
                customer.getRoute(),
                customer.getName(),
                front.getBarcode(),
                front.getHeight(),
                front.getWidth(),
                front.getQuantity()
        );
    }

    private static String valueOrNullString(Object obj) {
        return obj == null ? " null" : obj.toString();
    }
}
