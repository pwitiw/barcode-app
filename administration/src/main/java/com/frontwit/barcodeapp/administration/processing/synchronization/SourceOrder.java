package com.frontwit.barcodeapp.administration.processing.synchronization;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SourceOrder {
    private Long id;
    private String nr;
    private String fronts;
    private Date orderedAt;
    private Date deadline;
    private String type;
    private BigDecimal valuation;
    private String additionalInfo;
    private String description;
    private String features;
    private String route;
    private Long customerId;
    private String customerName;
    private String customerAddress;
    private String phoneNumber;

    public Date getOrderedAt() {
        return Date.from(orderedAt.toInstant());
    }

    public Date getDeadline() {
        if (deadline != null) {
            return Date.from(deadline.toInstant());
        }
        return null;
    }

    public void setOrderedAt(Date orderedAt) {
        if (orderedAt != null) {
            this.orderedAt = Date.from(orderedAt.toInstant());
        }
    }

    public void setDeadline(Date deadline) {
        if (deadline != null) {
            this.deadline = Date.from(deadline.toInstant());
        }
    }
}
