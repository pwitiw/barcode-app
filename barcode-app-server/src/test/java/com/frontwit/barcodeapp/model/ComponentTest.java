package com.frontwit.barcodeapp.model;

import com.frontwit.barcodeapp.datatype.Stage;
import com.frontwit.barcodeapp.logic.IllegalProcessingOrderException;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class ComponentTest {

    @Test
    public void shouldProcessAllComponentsSuccessfully() {
        //  given
        Component sut = Component.builder().quantity(2).processingHistory(new ArrayList<>()).build();
        //  when
        sut.applyProcess(Stage.MILLING, LocalDateTime.now());
        sut.applyProcess(Stage.MILLING, LocalDateTime.now());
        //  then
        assertThat(sut.getProcessingHistory(), hasSize(2));
        assertThat(sut.getProcessingHistory().get(0).getStage(), equalTo(Stage.MILLING));
        assertThat(sut.getProcessingHistory().get(1).getStage(), equalTo(Stage.MILLING));
    }

    @Test
    public void shouldAddProcessIfStageAlreadyExistButFurtherProcessed() {
        //  given
        Component sut = Component.builder().quantity(1).processingHistory(new ArrayList<>()).build();
        sut.applyProcess(Stage.MILLING, LocalDateTime.now());
        sut.applyProcess(Stage.POLISHING, LocalDateTime.now());
        //  when
        sut.applyProcess(Stage.MILLING, LocalDateTime.now());
        //  then
        assertThat(sut.getProcessingHistory(), hasSize(3));
    }

    @Test(expected = IllegalProcessingOrderException.class)
    public void shouldThrowExceptionIfAllComponentsProcessedAndNoFurtherProcesses() {
        //  given
        Component sut = Component.builder().quantity(2).processingHistory(new ArrayList<>()).build();
        sut.applyProcess(Stage.MILLING, LocalDateTime.now());
        sut.applyProcess(Stage.MILLING, LocalDateTime.now());
        sut.applyProcess(Stage.POLISHING, LocalDateTime.now());
        sut.applyProcess(Stage.POLISHING, LocalDateTime.now());
        //  when
        sut.applyProcess(Stage.POLISHING, LocalDateTime.now());
    }

    @Test(expected = IllegalProcessingOrderException.class)
    public void shouldThrowExceptionIfComponentAlreadyDelivered() {
        //  given
        Component sut = Component.builder().quantity(1).processingHistory(new ArrayList<>()).build();
        sut.applyProcess(Stage.DELIVERD, LocalDateTime.now());
        //  when
        sut.applyProcess(Stage.POLISHING, LocalDateTime.now());
    }
}
