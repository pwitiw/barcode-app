package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Page<OrderDto> getOrders(Pageable pageable);
}
