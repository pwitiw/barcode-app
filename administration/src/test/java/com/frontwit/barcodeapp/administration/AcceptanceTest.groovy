package com.frontwit.barcodeapp.administration

import com.fasterxml.jackson.databind.ObjectMapper
import com.frontwit.barcodeapp.administration.application.order.BarcodeConverter
import com.frontwit.barcodeapp.administration.application.order.Order
import com.frontwit.barcodeapp.administration.application.order.Stage
import com.frontwit.barcodeapp.administration.application.order.dto.OrderDetailDto
import com.frontwit.barcodeapp.administration.application.order.dto.ProcessCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

//class AcceptanceTest extends IntegrationSpec implements ProcessTrait {
//
//    long ORDER_ID = 1L;
//
//    @Autowired
//    ObjectMapper objectMapper
//
//    @Autowired
//    BarcodeConverter barcodeConverter
//
//    def "positive order processing scenario"() {
//        given: "Order to be processed"
//        def barcode = barcodeConverter.toBarcode(ORDER_ID)
//        def order = orderIsCreated(ORDER_ID)
//        def res = asJson(new HashSet<ProcessCommand>(Arrays.asList(new ProcessCommand(1L, Stage.BASE.id, LocalDateTime.now()))))
//        when: "I process components (milling)"
//        processingIsSuccessfullyRequested(aJsonProcessCommands(order, Stage.MILLING, objectMapper))
//
//        then: "Process has been registered and order status is milling"
//        orderStageIsUpgraded(Stage.MILLING)
//        orderStageIsUpgraded(Stage.BASE)
//    }
//
//    void orderIsCreated(long id) {
//        return new Order();
//    }
//
//    void processingIsSuccessfullyRequested(String processCommands) {
//        def request =
//                post("/api/process")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(processCommands)
//        mockMvc
//                .perform(request)
//                .andExpect(status().isOk())
//    }
//
//    void orderStageIsUpgraded(Stage stage) {
//        def request = get("/api/orders/" + ORDER_ID)
//        def result = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andReturn()
//        def dto = objectMapper.readValue(result.response.contentAsString, OrderDetailDto)
//        assert result.response.contentAsString.equals("{\"id\":1,\"name\":\"TW 100\",\"color\":null,\"size\":\"18MM\",\"cutter\":\"PŁYTA\",\"comment\":\"expressnull\",\"customer\":\"Jan Kowalski\",\"stage\":\"MILLING\",\"orderedAt\":null,\"components\":[]}");
//    }
//
//    def asJson(Set<ProcessCommand> commands) {
//        return objectMapper.writeValueAsString(commands)
//    }
//
//    def commandsForOrder(Order order, Stage stage, LocalDateTime time) {
//        return order.components.each { component ->
//            def list = component.quantity * new ProcessCommand(component.barcode, time)
//            return list
//        }
//    }
//
//}
