package com.frontwit.barcodeapp.domain.order;

import com.frontwit.barcodeapp.domain.order.dto.IllegalProcessingOrderException;
import com.frontwit.barcodeapp.domain.order.dto.ProcessCommand;
import com.frontwit.barcodeapp.domain.order.dto.ProcessNotAllowedException;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ComponentTest {

    private static final Long BARCODE = 1L;

    @Test
    public void processIsApplicableWhenSameBarcode() {
        //  given
        ProcessCommand processWithSameBarcode = new ProcessCommand(BARCODE, 1, LocalDateTime.now());
        ProcessCommand processWithDifferentBarcode =
                new ProcessCommand(BARCODE + 2, 1, LocalDateTime.now());
        Component sut = createComponent(1);
        //  then
        assertTrue(sut.isApplicable(processWithSameBarcode));
        assertFalse(sut.isApplicable(processWithDifferentBarcode));
    }

    @Test
    public void shouldApplyProcess() {
        //  given
        ProcessCommand process = new ProcessCommand(BARCODE, 1, LocalDateTime.now());
        Component sut = createComponent(2);
        //  when
        sut.apply(process);
        //  then
        assertFalse(sut.isComplete());
    }

    @Test(expected = IllegalProcessingOrderException.class)
    public void shouldNotApplyFirstProcessWhenNotFirstStage() {
        //  given
        ProcessCommand process = new ProcessCommand(BARCODE, 2, LocalDateTime.now());
        Component sut = createComponent(1);
        //  when
        sut.apply(process);
    }

    @Test(expected = IllegalProcessingOrderException.class)
    public void shouldNotApplyProcessWhenNotInOrder() {
        //  given
        ProcessCommand process1 = new ProcessCommand(BARCODE, 1, LocalDateTime.now());
        ProcessCommand process2 = new ProcessCommand(BARCODE, 3, LocalDateTime.now());
        Component sut = createComponent(1);
        sut.apply(process1);
        //  when
        sut.apply(process2);
    }

    @Test(expected = ProcessNotAllowedException.class)
    public void shouldNotApplyWhenAllComponentsAlreadyProcessedOnSpecifiedStage() {
        //  given
        Component sut = createComponent(1);
        ProcessCommand process1 = new ProcessCommand(BARCODE, 1, LocalDateTime.now());
        ProcessCommand process2 = new ProcessCommand(BARCODE, 1, LocalDateTime.now());
        sut.apply(process1);
        //  when
        sut.apply(process2);
    }

    @Test
    public void shouldApplyWhenCorrectionOnFullyCoveredStage() {
        //  given
        Component sut = createComponent(1);
        ProcessCommand process1 = new ProcessCommand(BARCODE, 1, LocalDateTime.now());
        ProcessCommand process2 = new ProcessCommand(BARCODE, 2, LocalDateTime.now());
        ProcessCommand process3 = new ProcessCommand(BARCODE, 1, LocalDateTime.now());
        sut.apply(process1);
        sut.apply(process2);
        //  when
        sut.apply(process3);
    }

    @Test
    public void isIncompleteWhenNotAllQuantitiesProcessed() {
        //  given
        Component sut = createComponent(2);
        ProcessCommand process1 = new ProcessCommand(BARCODE, 1, LocalDateTime.now());
        sut.apply(process1);
        //  when
        boolean result = sut.isComplete();
        //  then
        assertFalse(result);
    }

    @Test
    public void isCompleteWhenAllQuantitiesProcessed() {
        //  given
        Component sut = createComponent(2);
        ProcessCommand process1 = new ProcessCommand(BARCODE, 1, LocalDateTime.now());
        ProcessCommand process2 = new ProcessCommand(BARCODE, 1, LocalDateTime.now());
        sut.apply(process1);
        sut.apply(process2);
        //  when
        boolean result = sut.isComplete();
        //  then
        assertTrue(result);
    }

    private Component createComponent(int quantity) {
        return Component.builder()
                .barcode(BARCODE)
                .quantity(quantity)
                .processingHistory(new ArrayList<>()).build();
    }
}
