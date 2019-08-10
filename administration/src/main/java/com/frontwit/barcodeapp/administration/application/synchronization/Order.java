package com.frontwit.barcodeapp.administration.application.synchronization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.administration.application.order.dto.OrderDetailDto;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@NoArgsConstructor
class Order {
    private static final Logger LOG = LoggerFactory.getLogger(Order.class);

    private Long id;
    private String nr;
    private String components;
    private LocalDate orderedAt;
    private String additionalInfo;
    private String description;
    private String features;
    private String customer;

    static Order valueOf(Object[] fields) {
        Order o = new Order();
        o.id = (long) (int) fields[0];
        o.nr = (String) fields[1];
        o.components = (String) fields[2];
        o.orderedAt = ((Date) fields[3]).toLocalDate();
        o.additionalInfo = (String) fields[4];
        o.description = (String) fields[5];
        o.features = (String) fields[6];
        o.customer = (String) fields[7];
        return o;
    }

    OrderDetailDto composeOrderDetailDto(ObjectMapper objectMapper, Dictionary dictionary) {
        Features orderFeatures = new Features(features, dictionary, objectMapper);
        Components orderComponents = new Components(components, objectMapper);
        String comment = getComment();
        return OrderDetailDto.builder()
                .id(id)
                .name(nr)
                .orderedAt(orderedAt)
                .customer(customer)
                .components(orderComponents.composeComponents())
                .comment(comment)
                .cutter(orderFeatures.getCutter())
                .color(orderFeatures.getColor())
                .size(orderFeatures.getSize())
                .build();
    }

    private String getComment() {
        String separator = additionalInfo != null && description != null ? "; " : "";
        String comment = Optional.ofNullable(description).orElse("");
        return comment + separator + additionalInfo;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", nr='" + nr + '\'' +
                ", customer='" + customer + '\'' +
                '}';
    }
}
