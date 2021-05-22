package com.frontwit.barcodeapp.administration.infrastructure.db;

import com.frontwit.barcodeapp.administration.domain.qr.QrCodeInfo;
import com.frontwit.barcodeapp.administration.domain.qr.QrCodeInfoProvider;
import com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity;
import com.frontwit.barcodeapp.administration.processing.order.infrastructure.OrderEntity;
import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.Dimensions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MongoQrCodeInfoProvider implements QrCodeInfoProvider {

    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<QrCodeInfo> find(Barcode barcode) {
        var orderId = barcode.getOrderId().getId();
        var order = mongoTemplate.findById(orderId, OrderEntity.class);
        var front = mongoTemplate.findById(barcode.getBarcode(), FrontEntity.class);
        if (order == null || front == null) {
            return Optional.empty();
        }
        var customer = mongoTemplate.findById(order.getCustomerId(), CustomerEntity.class);

        return Optional.of(qrCodeInfo(order, front, customer));
    }

    private static QrCodeInfo qrCodeInfo(OrderEntity order, FrontEntity front, CustomerEntity customer) {
        return new QrCodeInfo(
                new QrCodeInfo.QrOrder(
                        order.getId(),
                        order.getName(),
                        order.getQuantity(),
                        customer.getRoute(),
                        customer.getName()
                ),
                new QrCodeInfo.QrFront(
                        front.getBarcode(),
                        new Dimensions(front.getHeight(), front.getWidth()),
                        front.getQuantity()
                )
        );
    }
}
