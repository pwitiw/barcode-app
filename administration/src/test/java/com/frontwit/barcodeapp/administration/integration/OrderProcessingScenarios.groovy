//package com.frontwit.barcodeapp.administration.integration
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.frontwit.barcodeapp.administration.application.order.OrderFacade
//import com.frontwit.barcodeapp.administration.application.order.Stage
//import com.frontwit.barcodeapp.administration.application.order.dto.OrderDetailDto
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.MediaType
//import org.springframework.test.web.servlet.RequestBuilder
//import org.springframework.test.web.servlet.ResultActions
//
//import static com.frontwit.barcodeapp.administration.integration.Fixtures.aProcessCommandJson
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//
//class OrderProcessingScenarios extends IntegrationSpec {
//
//    @Autowired
//    OrderFacade orderFacade
//
//    @Autowired
//    ObjectMapper objectMapper
//
//    def "process order should synchronize and process order"() {
//        given:
//        long orderId = 1L
//        String json = aProcessCommandJson(orderId, objectMapper)
//
//        when: "process commands are sent"
//        RequestBuilder request = post("/api/process")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("[${json}]")
//        ResultActions processOrder = mockMvc.perform(request)
//
//        then: "return status is OK = 200"
//        processOrder.andExpect(status().isOk())
//        // TODO spr wszystkie propertiesy
//        and: "orders are synchronized and processed"
//        OrderDetailDto result = orderFacade.findOne(orderId)
//        result != null
//        result.id == orderId
//        Stage.valueOf(result.stage) == Stage.MILLING
//    }
//
//}
