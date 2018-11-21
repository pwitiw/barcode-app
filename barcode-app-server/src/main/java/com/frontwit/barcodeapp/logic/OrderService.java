package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dto.OrderDetailDto;
import com.frontwit.barcodeapp.dto.OrderDto;
import com.frontwit.barcodeapp.dto.OrderSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    /**
     * Finds orders for specified page.
     *
     * @param pageable page details
     * @return list of orders
     */
    Page<OrderDto> getOrders(Pageable pageable);

    /**
     * Finds orders for specified page and criteria.
     *
     * @param pageable       page details
     * @param searchCriteria criteria for orders
     * @return list of orders
     */
    Page<OrderDto> getOrders(Pageable pageable, OrderSearchCriteria searchCriteria);

    /**
     * Finds order for given id.
     *
     * @param id of order
     * @return order details
     */
    OrderDetailDto getOrder(String id);


    /**
     * Save new order.
     *
     * @param order
     */
    void save(List<OrderDetailDto> order);
}
