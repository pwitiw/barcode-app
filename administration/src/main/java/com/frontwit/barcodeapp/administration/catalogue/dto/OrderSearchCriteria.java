package com.frontwit.barcodeapp.administration.catalogue.dto;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

import static org.springframework.util.StringUtils.*;


@Getter
@ToString
public class OrderSearchCriteria {
    String name;
    Boolean completed;
    Boolean packed;
    Stage stage;
    String customer;
    String route;
    LocalDate processingDate;
    LocalDate orderedAt;

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
