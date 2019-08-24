package com.frontwit.barcodeapp.administration

import com.frontwit.barcodeapp.administration.application.order.Order
import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@TypeChecked
@SpringBootTest(classes = [AdministrationApplication], webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
abstract class IntegrationSpec extends Specification {

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    WebApplicationContext webApplicationContext

    MockMvc mockMvc

    def setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        mongoTemplate.dropCollection(Order.class)
    }

    def tearDown() throws Exception {
        mongoTemplate.dropCollection(Order.class);
    }
}
