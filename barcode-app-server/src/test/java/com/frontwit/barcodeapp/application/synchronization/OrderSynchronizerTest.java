//package com.frontwit.barcodeapp.application.synchronization;
//
//import com.frontwit.barcodeapp.application.order.dto.OrderDetailDto;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class OrderSynchronizerTest {
//
//    private static final long ID = 1L;
//    private static final String NR = "Test Nr";
//    private static final LocalDateTime ORDER_DATE = LocalDateTime.now();
//    private static final String CUSTOMER_NR = "Additional comment";
//    private static final String CUSTOMER = "Customer name";
//    private static final String DESCRIPTION = "Description";
//
//    private static final int CUTTER_ID = 24;
//    private static final int SIZE_ID = 31;
//    private static final int COLOR_ID = 2;
//    private static final int SIDE_ID = 1;
//
//    private static final int C_ID = 1;
//    private static final int C_LENGTH = 1750;
//    private static final int C_WIDTH = 500;
//    private static final int C_QUANTITY = 1;
//    private static final int C_CUTTER = 24;
//    private static final int C_SIZE = 31;
//    private static final String C_COMMENT = "Comment";
//    private static final int C_COLOR = 59;
//    private static final int C_SIDE = 1;
//    private static final String C_ELEMENT = "";
//
//    private static final String FEATURES =
//            "{" +
//                    "\"cu\":\"" + CUTTER_ID + "\"," +
//                    "\"si\":\"" + SIZE_ID + "\"," +
//                    "\"co\":\"" + COLOR_ID + "\"," +
//                    "\"do\":\"" + SIDE_ID + "\"" +
//                    "}";
//
//    private static final String COMPONENTS =
//            "[{" +
//                    "\"nr\":\"" + C_ID + "\"," +
//                    "\"l\":\"" + C_LENGTH + "\"," +
//                    "\"w\":\"" + C_WIDTH + "\"," +
//                    "\"q\":\"" + C_QUANTITY + "\"," +
//                    "\"el\":\"" + C_ELEMENT + "\"," +
//                    "\"cu\":\"" + C_CUTTER + "\"," +
//                    "\"si\":\"" + C_SIZE + "\"," +
//                    "\"do\":" + C_SIDE + "," +
//                    "\"co\":\"" + C_COLOR + "\"," +
//                    "\"com\":\"" + C_COMMENT + "\"" +
//                    "}]";
//
//    private SynchronizationFacade sut;
//    private SynchronizationRepository repository;
//
//    @Before
//    public void setUp() {
//        repository = mock(SynchronizationRepository.class);
//        sut = new SynchronizationFacade(null, repository);
//    }
//
//    @Test
//    public void shouldCorrectlySynchronizeOrder() {
//        //  given
//        when(repository.findOrder(anyLong())).thenReturn(mockedResultFromDb());
//        //  when
//        OrderDetailDto result = sut.getOrder(ID);
//        //  then
//        assertEquals(DESCRIPTION + "; " + CUSTOMER_NR, result.getComment());
//        assertEquals(NR, result.getName());
//        assertEquals(LocalDateTime.parse(String.valueOf(ORDER_DATE)), result.getName());
//    }
//
//    private OrderDetailDto composeOrder() {
////
////        ComponentDto component = ComponentDto.builder()
////                .barcode(BarcodeConverter.toBarcode(ID) + C_ID)
////                .comment(C_COMMENT)
////                .height(C_LENGTH)
////                .width(C_WIDTH)
////                .quantity(C_QUANTITY)
////                .build();
////        return OrderDetailDto.builder()
////                .color(String.valueOf(COLOR_ID))
////                .components(Arrays.asList(component))
////                .name(NR)
////                .customer(CUSTOMER)
////                .build();
//        return null;
//    }
//
//    private Optional<Order> mockedResultFromDb() {
//        Order order = Order.builder()
//                .id(ID)
//                .nr(NR)
//                .components(COMPONENTS)
//                .customer(CUSTOMER)
//                .description(DESCRIPTION)
//                .additionalInfo(CUSTOMER_NR)
//                .orderedAt(ORDER_DATE)
//                .features(FEATURES)
//                .build();
//        return Optional.of(order);
//    }
//}
