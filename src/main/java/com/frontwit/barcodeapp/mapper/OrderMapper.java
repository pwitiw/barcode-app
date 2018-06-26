package com.frontwit.barcodeapp.mapper;

import com.frontwit.barcodeapp.dto.OrderDto;
import com.frontwit.barcodeapp.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order map(OrderDto dto);

    OrderDto map(Order order);
}
