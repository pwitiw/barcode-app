package com.frontwit.barcodeapp.domain.order;

import com.frontwit.barcodeapp.InMemoryOrderRepository;
import com.frontwit.barcodeapp.domain.order.dto.OrderDetailDto;
import com.frontwit.barcodeapp.domain.order.dto.OrderNotFoundException;
import com.frontwit.barcodeapp.domain.order.ports.OrderRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderReadServiceTest {

    private static final Long ID = 1L;
    private OrderReadService sut;
    private OrderRepository orderRepository;

    @Before
    public void setUp() {
        orderRepository = new InMemoryOrderRepository();
        sut = new OrderReadService(orderRepository);
    }

    @Test
    public void returnsOrderDetailsForId() {
        //  given
        Order order = Order.builder()
                .id(ID)
                .build();
        orderRepository.save(order);
        //  when
        OrderDetailDto result = sut.findOne(ID);
        //  then
        assertEquals(order.detailDto(), result);
    }

    @Test(expected = OrderNotFoundException.class)
    public void throwsExceptionWhenNoOrderForId() {
        //  when
        sut.findOne(ID);
    }

}
