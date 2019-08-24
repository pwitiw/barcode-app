package com.frontwit.barcodeapp.administration

import com.fasterxml.jackson.databind.ObjectMapper
import com.frontwit.barcodeapp.administration.application.order.Stage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AcceptanceTest extends IntegrationSpec implements ProcessTrait {

    Long ORDER_ID = 1L

    @Autowired
    ObjectMapper objectMapper

    def "positive order processing scenario"() {
        given: "Order to be processed"
//        def barcode = BarcodeConverterImpl.e(aJsonProcessCommand())
        orderIsCreated(ORDER_ID)

        when: "I process components (milling)"
        processingIsSuccessfullyRequested(aJsonProcessCommand(ORDER_ID, Stage.MILLING, objectMapper))

        then: "Process has been registered and order status is milling"
        orderStageIsUpgraded()
    }

    void orderIsCreated(long id) {

    }

    void processingIsSuccessfullyRequested(String processCommands) {
        RequestBuilder request =
                post("/api/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(processCommands)
        ResultActions process = mockMvc.perform(request)
        assert process.andExpect(status().isOk())
    }

    void orderStageIsUpgraded() {

    }

}
