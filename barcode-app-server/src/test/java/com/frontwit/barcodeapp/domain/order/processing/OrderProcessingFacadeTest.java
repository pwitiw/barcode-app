package com.frontwit.barcodeapp.domain.order.processing;

import com.frontwit.barcodeapp.domain.order.processing.dto.ProcessCommand;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

public class OrderProcessingFacadeTest {

    private static long BARCODE = 100L;

    /* TODO
     * jak nie ma to wywolanie synchronizacji (sprobujmy eventami)
     * jak jest to updatujemy
     *
     * */
    private OrderProcessingFacade sut;

    @Before
    public void setUp() {
        sut = new OrderProcessingFacade();
    }

    @Test
    public void shouldSynchronizeAndUpdateOrderWhenNotInSystem() {
        //  given
        ProcessCommand process = new ProcessCommand(BARCODE, 1, LocalDateTime.now());
        //  when
        sut.update(Arrays.asList(process));
        //  then

    }

    @Test
    public void shouldUpdateOrderWhenAlreadyInSystem() {
        //  given

        //  when

        //  then
    }


}
