package com.frontwit.barcodeapp.administration.order.processing.order.application.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;


@Getter
@ToString
public class OrderSearchCriteria {

    String name;
    Boolean completed;

    public boolean empty() {
        return StringUtils.isEmpty(this.name)
                && completed == null;
    }
}
