package com.frontwit.barcodeapp.administration.integration.base

import com.frontwit.barcodeapp.server.ServerApplication
import com.frontwit.barcodeapp.server.infrastructure.config.Profiles
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import javax.transaction.Transactional

@SpringBootTest(classes = [ServerApplication])
@ActiveProfiles([Profiles.TEST])
@Transactional
@Rollback
abstract class IntegrationSpec extends Specification {

    @Autowired
    protected WebApplicationContext webApplicationContext

    MockMvc mockMvc

    @Before
    void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build()
    }
}
