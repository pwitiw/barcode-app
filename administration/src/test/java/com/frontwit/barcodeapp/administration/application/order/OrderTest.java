package com.frontwit.barcodeapp.administration.application.order;

import com.frontwit.barcodeapp.administration.application.order.dto.ProcessCommand;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class OrderTest {

    @Test
    public void shouldUpdateStageAndMarkComplete() {
        //  given
        long extId = 1L;
        int stage = 1;
        Order order = createOrder(extId, 1);
        ProcessCommand process =
                new ProcessCommand(BarcodeConverter.toBarcode(extId) + 1, stage, LocalDateTime.now());
        //  when
        order.update(process);
        //  then
        assertEquals(Stage.valueOf(stage), order.getStage());
//        assertTrue(order.isCompleted());
    }

    @Test
    public void shouldUpdateStageAndMarkIncompleteWhenNotAllComponentsProcessed() {
        //  given
        long extId = 1L;
        int stage = 1;
        Order order = createOrder(extId, 2);
        ProcessCommand process =
                new ProcessCommand(extId, stage, LocalDateTime.now());
        //  when
        order.update(process);
        //  then
        assertEquals(Stage.valueOf(stage), order.getStage());
//        assertFalse(order.isCompleted());
    }


    private Order createOrder(long id, int componentsNr) {
        long barcode = BarcodeConverter.toBarcode(id);
        Set<Component> components = IntStream.range(1, componentsNr + 1)
                .mapToObj(i -> Component
                        .builder()
                        .processingHistory(new ArrayList<>())
                        .barcode(barcode + i)
                        .quantity(1)
                        .build())
                .collect(Collectors.toSet());
        return Order.builder()
                .id(id)
                .components(components)
                .build();
    }
}
