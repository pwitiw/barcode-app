db.createUser(
    {
        user: "patryk",
        pwd: "password",
        roles: [
            {
                role: "readWrite",
                db: "frontwit"
            }
        ]
    }
);

db.stages.insertMany([
    {
        "_id": "123e4567-e891-12d3-a456-426614174000",
        "meters": 3500.0,
        "period": ISODate("2021-06-30T14:00:00.00Z"),
        "stage": "PACKING"
    },
    {
            "_id": "123e4567-e89b-12d1-a456-426614174000",
            "meters": 6500.0,
            "period": ISODate("2021-06-30T15:05:00.00Z"),
            "stage": "PACKING"
        },
    {
            "_id": "123e4567-e89b-12d3-a456-126614174000",
            "meters": 3500.0,
            "period": ISODate("2021-06-30T16:48:00.00Z"),
            "stage": "PACKING"
        },
    {
            "_id": "123e4567-e89b-12d3-a456-426614171000",
            "meters": 1500.0,
            "period": ISODate("2021-06-30T17:00:00.00Z"),
            "stage": "PACKING"
        },
    {
                "_id": "123e4567-e89b-12d3-a456-426614174000",
                "meters": 10000.0,
                "period": ISODate("2021-06-29T10:00:00.00Z"),
                "stage": "PACKING"
            }

]);

db.user.insert({
    "_id": ObjectId("5d8c654890f5443c5e721ff1"),
    "username": "patryk",
    "password": "$2a$10$purbCrPMhLECPuLg4o5G6eaQgIYI7GfGsbFzYI8oryhlf6FSlFifC",
    "roles": [
        "USER"
    ],
    "_class": "com.frontwit.barcodeapp.administration.infrastructure.security.User"
});
db.customer.insertMany([
    {
        "_id": 1,
        "name": "Kowalski 1",
        "address": "Warszawa",
        "route": "Warszawa"
    },
    {
        "_id": 2,
        "name": "Kowalski 2",
        "address": "Lodz",
        "route": "Warszawa"
    },
    {
        "_id": 3,
        "name": "Nowak",
        "address": "Kraków",
        "route": "Kraków"
    }
]);
db.order.insertMany([
    {
        "_id": 1,
        "name": "TW 101",
        "orderedAt": ISODate("2020-12-14T22:00:00.000+0000"),
        "color": "CZARNY",
        "size": "18MM",
        "stage": "MILLING",
        "cutter": "PŁYTA",
        "comment": "express",
        "customerId": 1,
        "completed": false,
        "quantity": 2,
        "type": "COMPLAINT",
        "lastProcessedOn": ISODate("2019-12-22T23:00:00.000+0000"),
        "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
    },
    {
        "_id": 2,
        "name": "TW 201",
        "orderedAt": ISODate("2020-12-16T22:00:00.000+0000"),
        "color": "BIAŁY",
        "size": "18MM",
        "stage": "PACKING",
        "cutter": "PŁYTA",
        "comment": "express",
        "customerId": 2,
        "completed": false,
        "packed": true,
        "quantity": 2,
        "lastProcessedOn": ISODate("2019-16-22T23:00:00.000+0000"),
        "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
    }, {
        "_id": 3,
        "name": "TW 201",
        "orderedAt": ISODate("2020-12-15T22:00:00.000+0000"),
        "color": "BIAŁY",
        "size": "18MM",
        "stage": "PACKING",
        "cutter": "PŁYTA",
        "comment": "express",
        "customerId": 3,
        "completed": false,
        "packed": true,
        "quantity": 2,
        "valuation": 300.02,
        "lastProcessedOn": ISODate("2019-12-22T23:00:00.000+0000"),
        "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
    }
]);
db.front.insertMany([
    {
        "_id": 101,
        "orderId": 1,
        "height": 1456,
        "width": 2389,
        "quantity": 2,
        "stage": "PACKING",
        "comment": "casadas",
        "processings": [
            {
                "stage": "BASE",
                "dateTime": ISODate("2019-12-21T23:09:35.563+0000")
            }
        ],
        "amendments": [
            {
                "stage": "BASE",
                "dateTime": ISODate("2019-12-21T23:09:55.129+0000")
            }
        ],
        "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
    },
    {
        "_id": 201,
        "orderId": 2,
        "height": 100,
        "width": 200,
        "quantity": 1,
        "stage": "PACKING",
        "comment": "casadas",
        "processings": [
            {
                "stage": "PACKING",
                "dateTime": ISODate("2019-12-21T23:09:35.563+0000")
            }
        ],
        "amendments": [],
        "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
    },
    {
        "_id": 301,
        "orderId": 3,
        "height": 220,
        "width": 204,
        "quantity": 1,
        "stage": "PACKING",
        "comment": "casadas",
        "processings": [
            {
                "stage": "PACKING",
                "dateTime": ISODate("2019-12-21T23:09:35.563+0000")
            }
        ],
        "amendments": [],
        "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
    }
]);