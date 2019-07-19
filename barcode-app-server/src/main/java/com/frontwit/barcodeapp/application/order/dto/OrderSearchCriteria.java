package com.frontwit.barcodeapp.application.order.dto;

import lombok.Getter;
import org.springframework.util.StringUtils;


@Getter
public class OrderSearchCriteria {

    String name;

    public boolean empty() {
        return StringUtils.isEmpty(this.name);
    }

    @Override
    public String toString() {
        return "OrderSearchCriteria{" +
                "name='" + name + '\'' +
                '}';
    }
}
