package com.frontwit.barcodeapp.administration.processing.synchronization;

import com.frontwit.barcodeapp.administration.processing.shared.OrderId;
import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Value
public class TargetOrder {
    private OrderId orderId;
    private Long customerId;
    private TargetOrder.Comment comment;
    private TargetOrder.Info info;
    private List<TargetFront> fronts;

    @Value
    public static class Comment {

        private String description;
        private String additionalInfo;

        public String compose() {
            String separator = additionalInfo != null && description != null ? "; " : "";
            String desc = Optional.ofNullable(description).orElse("");
            String info = Optional.ofNullable(additionalInfo).orElse("");
            return desc + separator + info;
        }

    }

    @Value
    public static class Info {
        private String color;
        private String cutter;
        private String size;
        private String name;
        private Instant orderedAt;
    }
}