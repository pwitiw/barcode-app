package com.frontwit.barcodeapp.model;

import com.frontwit.barcodeapp.datatype.Stage;
import com.frontwit.barcodeapp.logic.IllegalProcessingOrderException;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class ComponentTest {

    @Test
    public void shouldAddProcessIfStageDoesNotExistInHistory() {
        //  given
        Component sut = new Component();
        //  when
        sut.applyProcess(Stage.MILLING, LocalDateTime.now());
        //  then
        assertThat(sut.getProcessingHistory(), hasSize(1));
    }

    @Test
    public void shouldAddProcessIfStageAlreadyExistButFurtherProcessed() {
        //  given
        Component sut = new Component();
        sut.applyProcess(Stage.MILLING, LocalDateTime.now());
        sut.applyProcess(Stage.POLISHING, LocalDateTime.now());
        //  when
        sut.applyProcess(Stage.MILLING, LocalDateTime.now());
        //  then
        assertThat(sut.getProcessingHistory(), hasSize(3));
    }

    @Test(expected = IllegalProcessingOrderException.class)
    public void shouldThrowExceptionIfStageAlreadyExistAndNoFurtherProcesses() {
        //  given
        Component sut = new Component();
        sut.applyProcess(Stage.MILLING, LocalDateTime.now());
        sut.applyProcess(Stage.POLISHING, LocalDateTime.now());
        //  when
        sut.applyProcess(Stage.POLISHING, LocalDateTime.now());
    }

    @Test(expected = IllegalProcessingOrderException.class)
    public void shouldThrowExceptionIfComponentAlreadyDelivered() {
        //  given
        Component sut = new Component();
        sut.applyProcess(Stage.DELIVERD, LocalDateTime.now());
        //  when
        sut.applyProcess(Stage.POLISHING, LocalDateTime.now());
    }
}
