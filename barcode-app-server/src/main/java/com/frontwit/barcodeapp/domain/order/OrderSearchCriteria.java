package com.frontwit.barcodeapp.domain.order;

import org.springframework.util.StringUtils;

public class OrderSearchCriteria {

    public String name;
    public Long barcode;
    public String color;
    public String cutter;

    public static boolean isEmpty(OrderSearchCriteria searchCriteria) {
        return searchCriteria == null
                || (StringUtils.isEmpty(searchCriteria.name)
                && StringUtils.isEmpty(searchCriteria.barcode)
                && StringUtils.isEmpty(searchCriteria.color)
                && StringUtils.isEmpty(searchCriteria.cutter));
    }

    @Override
    public String toString() {
        return "OrderSearchCriteria{" +
                "name='" + name + '\'' +
                ", barcode='" + barcode + '\'' +
                ", color='" + color + '\'' +
                ", cutter='" + cutter + '\'' +
                '}';
    }
}
