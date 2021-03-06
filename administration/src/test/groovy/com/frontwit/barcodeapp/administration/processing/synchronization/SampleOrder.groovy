package com.frontwit.barcodeapp.administration.processing.synchronization

import java.time.Instant

trait SampleOrder {
    long ID = 1L
    long millis = 12345678L
    Instant ORDERED_AT = Instant.ofEpochMilli(12345678L)
    String NR = "TW 100"
    String ADDITIONAL_INFO = "additional info"
    String DESCRIPTION = "express"
    String CUSTOMER = "Jan Kowalski"
    String SIZE = "18MM"
    String COLOR = "Biały"
    String CUTTER = "PŁYTA"
    String ROUTE = "trasa"

    Integer NUMBER = 1
    Integer QUANTITY = 1
    Integer LENGTH = 1
    Integer WIDTH = 1
    String COMMENT = "comment"

    SourceOrder aSourceOrder() {
        SourceOrder order = new SourceOrder()
        order.setId(ID)
        order.setNr(NR)
        order.setFronts(
                "[{\"nr\":\"" + NUMBER + "\"," +
                        "\"l\":\"" + LENGTH + "\"," +
                        "\"w\":\"" + WIDTH + "\"," +
                        "\"q\":\"" + QUANTITY + "\"," +
                        "\"a\":\"0.484\",\"el\":\"\",\"cu\":\"2\",\"si\":\"1\",\"do\":1,\"co\":\"3\"," +
                        "\"com\":\"" + COMMENT + "\"}]"
        )
        order.setOrderedAt(new Date(millis))
        order.setAdditionalInfo(ADDITIONAL_INFO)
        order.setDescription(DESCRIPTION)
        order.setFeatures("{\"cu\":\"2\",\"si\":\"1\",\"co\":\"3\",\"do\":\"1\"}")
        order.setCustomerName(CUSTOMER)
        order.setRoute(ROUTE)
        return order
    }

    Dictionary aDictionary() {
        def entries = [
                new Dictionary.Entry(1, SIZE),
                new Dictionary.Entry(2, CUTTER),
                new Dictionary.Entry(3, COLOR),
        ]
        return new Dictionary(new ArrayList<Dictionary.Entry>(entries))
    }
}