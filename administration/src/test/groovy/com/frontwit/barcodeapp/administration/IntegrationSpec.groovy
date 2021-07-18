package com.frontwit.barcodeapp.administration


import com.frontwit.barcodeapp.api.shared.Barcode
import com.frontwit.barcodeapp.api.shared.OrderId
import com.frontwit.barcodeapp.administration.processing.shared.events.DomainEvents
import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

import static java.util.Collections.singletonList
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@TypeChecked
@SpringBootTest(classes = [AdministrationApplication], webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = "classpath:schema.sql")
@Testcontainers
abstract class IntegrationSpec extends Specification {
    private final static MongoDBContainer mongoDb = new MongoDBContainer()
    private final static MySQLContainer mysqlDb = new MySQLContainer()

    @Autowired
    WebApplicationContext webApplicationContext
    @Autowired
    MongoTemplate mongoTemplate
    @Autowired
    JdbcTemplate jdbcTemplate
    @Autowired
    DomainEvents domainEvents;

    MockMvc mockMvc

    Long ORDER_ID = 999L
    Barcode BARCODE = Barcode.valueOf(new OrderId(ORDER_ID), 1L)


    def "setupSpec"() {
        mongoDb.setPortBindings(singletonList("27017:27017"))
        mongoDb.start()
        mysqlDb.start()
    }

    def setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        jdbcTemplate.execute(
                "INSERT INTO tzamowienia (id, tklienci_id, data_z,  data_r, numer, opis, cechy, pozycje, nr_zam_kl)" +
                        "VALUES ($ORDER_ID, 1, '2019-06-07', '2019-06-08','TW 100', 'express', '{\"cu\":\"2\",\"si\":\"1\",\"co\":\"3\",\"do\":\"1\"}','[{\"nr\":\"1\",\"l\":\"1000\",\"w\":\"2000\",\"q\":\"1\",\"a\":\"0.484\",\"el\":\"\",\"cu\":\"2\",\"si\":\"1\",\"do\":1,\"co\":\"3\",\"com\":\"komentarz 1\"}]', NULL)")
        jdbcTemplate.execute("INSERT INTO tdictionary (id, name) VALUES (1,'18MM'),(2,'PLYTA'), (3,'BIALY'),(4,'CZARNY')")
        jdbcTemplate.execute("INSERT INTO tklienci (id, nazwa, adres, trasa, email, telefon) VALUES (1,'Jan Kowalsk', 'Wroclaw','Wroclaw', 'email@wp.pl','123456789')")
    }

    def cleanup() throws Exception {
        mongoTemplate.getDb().drop()
        jdbcTemplate.execute("DELETE FROM tzamowienia")
        jdbcTemplate.execute("DELETE FROM tdictionary")
        jdbcTemplate.execute("DELETE FROM tklienci")
    }
}
