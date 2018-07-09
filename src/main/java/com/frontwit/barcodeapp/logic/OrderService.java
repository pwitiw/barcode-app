package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dto.OrderDto;
import com.frontwit.barcodeapp.dto.OrderSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {


    /**
     * Finds orders for specified page and criteria.
     *
     * @param pageable page details
     * @param searchCriteria criteria for orders
     * @return list of orders
     */
    Page<OrderDto> getOrders(Pageable pageable, OrderSearchCriteria searchCriteria);
}
