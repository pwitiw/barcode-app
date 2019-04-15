//package com.frontwit.barcodeapp.zzzdeprecated;
//
//import com.frontwit.barcodeapp.domain.order.processing.dto.ProcessCommand;
//import com.frontwit.barcodeapp.domain.order.processing.ports.OrderRepository;
//import com.frontwit.barcodeapp.orderprocess.domain.Stage;
//import com.frontwit.barcodeapp.model.Order;
//import com.frontwit.barcodeapp.domain.order.processing.Process;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.containsInAnyOrder;
//import static org.hamcrest.Matchers.hasSize;
//
//public class OrderServiceTest {
//
//    private OrderService sut;
//    private OrderRepository orderRepository;
//
//    @Before
//    public void setUp() {
//        orderRepository = new InMemoryOrderDao();
//        sut = new OrderService(orderRepository, null, null);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void shouldThrowExceptionWhenNullPassed() {
//        //  when
//        sut.updateOrdersForProcesses(null);
//    }
//
//    @Test
//    public void shouldUpdateOrdersForBarcodes() {
//        //  given
//        Long barcode1 = BarcodeGeneratorService.MAX_ORDER_AMOUNT;
//        Long barcode2 = 2 * BarcodeGeneratorService.MAX_ORDER_AMOUNT;
//        Order order1 = TestData.createOrder(barcode1, 1);
//        Order order2 = TestData.createOrder(barcode2, 1);
//        orderRepository.save(order1);
//        orderRepository.save(order2);
//
//        LocalDateTime currentTime = LocalDateTime.now();
//        Stage stage1 = Stage.MILLING;
//        Stage stage2 = Stage.POLISHING;
//        List<ProcessCommand> dtos = new ArrayList<>();
//        dtos.add(new ProcessCommand(stage1.getId(), barcode1 + 1, currentTime));
//        dtos.add(new ProcessCommand(stage2.getId(), barcode1 + 1, currentTime));
//        dtos.add(new ProcessCommand(stage1.getId(), barcode2 + 1, currentTime));
//
//        //  when
//        sut.updateOrdersForProcesses(dtos);
//
//        //  then
//        List<Process> processes1 = orderRepository.findOne(order1.getBarcode()).getComponents().iterator().next()
//                .getProcessingHistory();
//        assertThat(processes1, hasSize(2));
//        assertThat(processes1, containsInAnyOrder(new Process(stage1, currentTime), new Process(stage2, currentTime)));
//
//        List<Process> processes2 = orderRepository.findOne(order2.getBarcode()).getComponents().iterator().next()
//                .getProcessingHistory();
//        assertThat(processes2, hasSize(1));
//        assertThat(processes2, containsInAnyOrder(new Process(stage1, currentTime)));
//    }
//
//
//
//}
