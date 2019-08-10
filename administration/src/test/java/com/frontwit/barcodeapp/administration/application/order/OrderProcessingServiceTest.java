package com.frontwit.barcodeapp.administration.application.order;

import com.frontwit.barcodeapp.administration.InMemoryOrderDao;
import com.frontwit.barcodeapp.administration.application.order.dto.OrderDetailDto;
import com.frontwit.barcodeapp.administration.application.order.dto.ProcessCommand;
import com.frontwit.barcodeapp.administration.application.ports.OrderDao;
import com.frontwit.barcodeapp.administration.application.synchronization.SynchronizationFacade;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class OrderProcessingServiceTest {

    private static long ID = 100L;

    private OrderProcessingService sut;
    private OrderDao orderDao;
    private SynchronizationFacade orderSynchronizer;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        orderSynchronizer = Mockito.mock(SynchronizationFacade.class);
        orderDao = new InMemoryOrderDao();
        sut = new OrderProcessingService(orderDao, orderSynchronizer);
    }

    @Test
    public void shouldSynchronizeAndUpdateOrderWhenNotInSystem() {
        //  given
        ProcessCommand process = new ProcessCommand(ID, 1, LocalDateTime.now());
        OrderDetailDto dto = OrderDetailDto.builder()
                .id(ID)
                .build();
        when(orderSynchronizer.getOrder(anyLong())).thenReturn(Optional.of(dto));
        //  when
        sut.update(Arrays.asList(process));
        //  then
        Optional<Order> optOrder = orderDao.findOne(ID);
        assertTrue(optOrder.isPresent());
        assertEquals(Long.valueOf(ID), optOrder.get().getId());
        assertEquals(Stage.valueOf(1), optOrder.get().getStage());
    }

    @Test
    @Ignore // todo pwitiw fix
    public void shouldUpdateOrderWhenAlreadyInSystem() {
        //  given
        ProcessCommand process = new ProcessCommand(ID, 1, LocalDateTime.now());
        Order order = Order.builder()
                .id(ID)
                .components(new HashSet<>())
                .build();
        orderDao.save(order);
        //  when
        sut.update(Arrays.asList(process));
        //  then
        Optional<Order> optOrder = orderDao.findOne(ID);
        assertTrue(optOrder.isPresent());
        assertEquals(Long.valueOf(ID), optOrder.get().getId());
        assertEquals(Stage.valueOf(1), optOrder.get().getStage());
    }


}
