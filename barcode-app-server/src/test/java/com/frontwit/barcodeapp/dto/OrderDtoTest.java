package com.frontwit.barcodeapp.dto;

import com.frontwit.barcodeapp.model.Component;
import com.frontwit.barcodeapp.model.Order;
import com.google.common.collect.Sets;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class OrderDtoTest {

    @Test
    public void shouldSuccessfullyConvertToDto() {
        //  given
        Component component1 = Component.builder().quantity(3).build();
        Component component2 = Component.builder().quantity(2).build();
        Order order = Order.builder().components(Sets.newHashSet(component1, component2)).build();
        //  when
        OrderDto orderDto = OrderDto.valueOf(order);
        //  then
        assertThat(orderDto.getQuantity(), equalTo(5));
    }
}
