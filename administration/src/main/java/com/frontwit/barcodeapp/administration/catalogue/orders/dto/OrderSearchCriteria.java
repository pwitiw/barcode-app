package com.frontwit.barcodeapp.administration.catalogue.orders.dto;

import com.frontwit.barcodeapp.api.shared.Stage;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

import static org.springframework.util.StringUtils.isEmpty;


@Getter
@ToString
public class OrderSearchCriteria {
    private String name;
    private Boolean completed;
    private Boolean packed;
    private Stage stage;
    private String customer;
    private String route;
    private LocalDate processingDate;
    private LocalDate orderedAt;

    @SuppressWarnings("BooleanExpressionComplexity")
    public boolean empty() {
        return isEmpty(this.name)
                && isEmpty(this.customer)
                && isEmpty(this.route)
                && completed == null
                && packed == null
                && stage == null
                && processingDate == null
                && orderedAt == null;
    }
}
