package com.frontwit.barcodeapp.administration

import OrderEntity
import com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity
import com.frontwit.barcodeapp.administration.processing.shared.Barcode
import com.frontwit.barcodeapp.administration.processing.shared.OrderId
import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@TypeChecked
@SpringBootTest(classes = [AdministrationApplication], webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@Rollback
abstract class IntegrationSpec extends Specification {

    Long ORDER_ID = 999L
    Barcode BARCODE = Barcode.valueOf(new OrderId(ORDER_ID), 1L)

    @Autowired
    WebApplicationContext webApplicationContext

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    JdbcTemplate jdbcTemplate

    MockMvc mockMvc

    def setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        initSourceDb()
    }

    def cleanup() throws Exception {
        mongoTemplate.remove(new Query(new Criteria("orderId").is(ORDER_ID)), FrontEntity.class)
        mongoTemplate.remove(new Query(new Criteria("id").is(ORDER_ID)), OrderEntity.class)
        jdbcTemplate.execute("DELETE FROM tzamowienia where id =$ORDER_ID")
    }

    def initSourceDb() {
        jdbcTemplate.execute(
                "INSERT INTO tzamowienia (id, tklienci_id, data_z, numer, opis, cechy, pozycje, nr_zam_kl)" +
                        "VALUES ($ORDER_ID, 1, '2019-06-07', 'TW 100', 'express', '{\"cu\":\"2\",\"si\":\"1\",\"co\":\"3\",\"do\":\"1\"}','[{\"nr\":\"1\",\"l\":\"1000\",\"w\":\"2000\",\"q\":\"1\",\"a\":\"0.484\",\"el\":\"\",\"cu\":\"2\",\"si\":\"1\",\"do\":1,\"co\":\"3\",\"com\":\"komentarz 1\"}]', NULL)")
    }

}
