package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.administration.processing.shared.Barcode;
import com.frontwit.barcodeapp.administration.processing.shared.OrderId;
import com.frontwit.barcodeapp.administration.processing.shared.Quantity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@AllArgsConstructor
public class OrderMapper {
    private static final Logger LOG = LoggerFactory.getLogger(OrderMapper.class);

    private ObjectMapper objectMapper;

    public TargetOrder map(SourceOrder source, Dictionary dictionary) {
        var orderId = new OrderId(source.getId());
        var customerId = source.getCustomerId();
        var valuation = source.getValuation();
        var deadline = Optional.ofNullable(source.getDeadline()).map(Date::toInstant).orElse(null);
        var comment = new TargetOrder.Comment(source.getDescription(), source.getAdditionalInfo());
        var orderInfo = createOrderInfo(source, dictionary);
        var fronts = createFronts(source);

        return new TargetOrder(orderId, customerId, valuation, deadline, comment, orderInfo, fronts);
    }

    private TargetOrder.Info createOrderInfo(SourceOrder source, Dictionary dictionary) {
        try {
            var features = objectMapper.readValue(source.getFeatures(), Features.class);
            var color = dictionary.getValue(features.getColor());
            var cutter = dictionary.getValue(features.getCutter());
            var size = dictionary.getValue(features.getSize());
            var type = OrderTypeMapper.map(source.getType());
            return new TargetOrder.Info(color, cutter, size, source.getNr(), source.getOrderedAt().toInstant(), type);
        } catch (IOException e) {
            LOG.warn(format("Order parsing error. Default order info set {orderId= %s}", source.getId()), e);
        }
        return new TargetOrder.Info("", "", "", source.getNr(), source.getOrderedAt().toInstant(), OrderTypeMapper.map(source.getType()));
    }

    private List<TargetFront> createFronts(SourceOrder source) {
        try {
            var orderId = new OrderId(source.getId());
            var fronts = objectMapper.readValue(source.getFronts(), Element[].class);
            return Stream.of(fronts).map(element -> createFront(orderId, element)).collect(Collectors.toList());
        } catch (IOException e) {
            LOG.warn(format("Fronts parsing error. Empty collection set {orderId= %s}", source.getId()), e);
        }
        return Collections.emptyList();
    }

    private TargetFront createFront(OrderId orderId, Element element) {
        var barcode = Barcode.valueOf(orderId, element.getNumber());
        var dimensions = new TargetFront.Dimensions(element.getLength(), element.getWidth());
        var quantity = new Quantity(element.getQuantity());
        return new TargetFront(barcode, orderId, quantity, dimensions, element.getComment());
    }

    @Data
    @JsonIgnoreProperties(value = "do")
    private static class Features {
        @JsonProperty("cu")
        private long cutter;
        @JsonProperty("si")
        private long size;
        @JsonProperty("co")
        private long color;
    }

    @Data
    @JsonIgnoreProperties(value = {"el", "a", "cu", "si", "do", "co"})
    private static class Element {
        @JsonProperty("nr")
        private long number;
        @JsonProperty("l")
        private int length;
        @JsonProperty("w")
        private int width;
        @JsonProperty("q")
        private int quantity;
        @JsonProperty("com")
        private String comment;
    }
}

