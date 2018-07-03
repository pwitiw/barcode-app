package com.frontwit.barcodeapp.dto;

import java.time.LocalDate;

public class OrderSearchCriteria {

    public String name;

    public LocalDate orderedFrom;

    public LocalDate orderedTo;

    public static boolean isEmpty(OrderSearchCriteria searchCriteria) {
        return searchCriteria == null
                || (searchCriteria.name == null || searchCriteria.name.equals(""))
                && searchCriteria.orderedFrom == null
                && searchCriteria.orderedTo == null;
    }
}
