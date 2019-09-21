package com.frontwit.barcodeapp.administration.order.processing.synchronization;

import com.frontwit.barcodeapp.administration.order.processing.shared.OrderId;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Value
public class TargetOrder {
    private OrderId orderId;
    private TargetOrder.Comment comment;
    private TargetOrder.Info info;
    private List<TargetFront> fronts;

    @Value
    public static class Comment {

        private String description;
        private String additionalInfo;

        public String compose() {
            String separator = additionalInfo != null && description != null ? "; " : "";
            String comment = Optional.ofNullable(description).orElse("");
            return comment + separator + additionalInfo;
        }

    }

    @Value
    public static class Info {
        private String color;
        private String cutter;
        private String size;
        private String name;
        private String customer;
        private LocalDate orderedAt;
    }

}