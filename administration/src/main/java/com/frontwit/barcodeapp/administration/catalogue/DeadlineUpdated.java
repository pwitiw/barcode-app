package com.frontwit.barcodeapp.administration.catalogue;

import lombok.Value;

import java.time.Instant;

@Value
class DeadlineUpdated {
    Long orderId;
    Instant deadline;

    static DeadlineUpdated of(long orderId, long deadline) {
        return new DeadlineUpdated(orderId, Instant.ofEpochMilli(deadline));
    }
}