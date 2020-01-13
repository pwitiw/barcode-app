package com.frontwit.barcodeapp.administration.catalogue.dto;

import com.frontwit.barcodeapp.administration.processing.shared.Stage;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.time.LocalDate;


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

    public boolean empty() {
        return StringUtils.isEmpty(this.name)
                && completed == null
                && stage == null
                && customer == null
                && route == null
                && processingDate == null
                && packed == null;
    }
}
