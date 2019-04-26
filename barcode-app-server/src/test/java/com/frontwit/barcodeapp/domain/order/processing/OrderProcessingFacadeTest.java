package com.frontwit.barcodeapp.domain.order.processing;

import com.frontwit.barcodeapp.InMemoryOrderRepository;
import com.frontwit.barcodeapp.domain.order.OrderDetailDto;
import com.frontwit.barcodeapp.domain.order.processing.dto.OrderNotFoundException;
import com.frontwit.barcodeapp.domain.order.processing.dto.ProcessCommand;
import com.frontwit.barcodeapp.domain.order.processing.ports.OrderRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderProcessingFacadeTest {

    private static long EXT_ID = 100L;

    /* TODO
     * jak nie ma to wywolanie synchronizacji (sprobujmy eventami)
     * jak jest to updatujemy
     *
     *
     * -- czy stosowac w ogole barcode  czy wystarczy extId
     * -- dockercompose do stawiania testowego mongo, mysql'a
     *  */
    private OrderProcessingFacade sut;
    private OrderRepository orderRepository;


    @Before
    public void setUp() {
        orderRepository = new InMemoryOrderRepository();
        sut = new OrderProcessingFacade();
    }

    @Test
    public void returnsOrderDetailsForGivenBarcode() {
        //  given
        long barcode = BarcodeGenerator.create(EXT_ID);
        Order order = new Order(barcode, "", null);
        orderRepository.save(order);
        //  when
        OrderDetailDto dto = sut.getOrder(barcode);
        //  then
        assertNotNull(dto);
//        assertNotNull(dto.getExtId(), order.getId());
    }

    @Test(expected = OrderNotFoundException.class)
    public void shouldThrowExceptionWhenNoOrderForGivenBarcode() {
        //  when
        sut.getOrder(BarcodeGenerator.create(EXT_ID));
    }

    @Test
    public void shouldSynchronizeAndUpdateOrderWhenNotInSystem() {
        //  given
        long barcode = BarcodeGenerator.create(EXT_ID);
        ProcessCommand process = new ProcessCommand(barcode, 1, LocalDateTime.now());
        //  when
        sut.update(Arrays.asList(process));
        //  then
        OrderDetailDto order = sut.getOrder(barcode);
        assertEquals(Long.valueOf(barcode), order.getBarcode());
        assertEquals(Stage.valueOf(1), order.getBarcode());
        assertEquals(1, order.getComponents().size());
    }

    @Test
    public void shouldUpdateOrderWhenAlreadyInSystem() {
        //  given

        //  when

        //  then
    }


}
