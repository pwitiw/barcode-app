package com.frontwit.barcodeapp.administration.application.order.dto;

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
                || completed != null;
    }
}
