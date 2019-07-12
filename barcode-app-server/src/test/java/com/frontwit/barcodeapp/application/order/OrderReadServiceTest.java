package com.frontwit.barcodeapp.application.order;

import com.frontwit.barcodeapp.InMemoryOrderDao;
import com.frontwit.barcodeapp.application.order.dto.OrderDetailDto;
import com.frontwit.barcodeapp.application.order.dto.OrderNotFoundException;
import com.frontwit.barcodeapp.application.ports.OrderDao;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class OrderReadServiceTest {

    private static final Long ID = 1L;
    private OrderReadService sut;
    private OrderDao orderDao;

    @Before
    public void setUp() {
        orderDao = new InMemoryOrderDao();
        sut = new OrderReadService(orderDao);
    }

    @Test
    public void returnsOrderDetailsForId() {
        //  given
        var order = Order.builder()
                .id(ID)
                .stage(Stage.MILLING)
                .components(Collections.emptySet())
                .build();
        orderDao.save(order);
        //  when
        var result = sut.findOne(ID);
        //  then
        assertEquals(order.detailDto(), result);
    }

    @Test(expected = OrderNotFoundException.class)
    public void throwsExceptionWhenNoOrderForId() {
        //  when
        sut.findOne(ID);
    }

}
