package com.frontwit.barcodeapp.logic;

import com.frontwit.barcodeapp.dao.OrderDao;
import com.frontwit.barcodeapp.datatype.Stage;
import com.frontwit.barcodeapp.dto.ProcessDto;
import com.frontwit.barcodeapp.model.Order;
import com.frontwit.barcodeapp.model.Process;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

public class OrderServiceTest {

    private OrderService sut;
    private OrderDao orderDao;

    @Before
    public void setUp() {
        orderDao = new InMemoryOrderDao();
        sut = new OrderService(orderDao, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNullPassed() {
        //  when
        sut.updateOrdersForProcesses(null);
    }

    @Test
    public void shouldUpdateOrdersForBarcodes() {
        //  given
        Long barcode1 = BarcodeGeneratorService.MAX_ORDER_AMOUNT;
        Long barcode2 = 2 * BarcodeGeneratorService.MAX_ORDER_AMOUNT;
        Order order1 = TestData.createOrder(barcode1, 1);
        Order order2 = TestData.createOrder(barcode2, 1);
        orderDao.save(order1);
        orderDao.save(order2);

        LocalTime currentTime = LocalTime.now();
        Stage stage1 = Stage.MILLING;
        Stage stage2 = Stage.POLISHING;
        List<ProcessDto> dtos = new ArrayList<>();
        dtos.add(new ProcessDto(stage1.getReaderId(), barcode1 + 1, currentTime));
        dtos.add(new ProcessDto(stage2.getReaderId(), barcode1 + 1, currentTime));
        dtos.add(new ProcessDto(stage1.getReaderId(), barcode2 + 1, currentTime));

        //  when
        sut.updateOrdersForProcesses(dtos);

        //  then
        List<Process> processes1 = orderDao.findOne(order1.getId().toHexString()).getComponents().iterator().next().getProcessingHistory();
        assertThat(processes1, hasSize(2));
        assertThat(processes1, containsInAnyOrder(new Process(stage1, currentTime), new Process(stage2, currentTime)));

        List<Process> processes2 = orderDao.findOne(order2.getId().toHexString()).getComponents().iterator().next().getProcessingHistory();
        assertThat(processes2, hasSize(1));
        assertThat(processes2, containsInAnyOrder(new Process(stage1, currentTime)));
    }


}
