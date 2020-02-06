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

db.user.insert({
    "_id": ObjectId("5d8c654890f5443c5e721ff1"),
    "username": "patryk",
    "password": "$2a$10$purbCrPMhLECPuLg4o5G6eaQgIYI7GfGsbFzYI8oryhlf6FSlFifC",
    "roles": [
        "USER"
    ],
    "_class": "com.frontwit.barcodeapp.administration.infrastructure.security.User"
});
// INSERT MANY
// db.order.insertMany([
//     {
//         "_id": 2,
//         "name": "TW 101",
//         "orderedAt": ISODate("2019-06-07T22:00:00.000+0000"),
//         "color": "CZARNY",
//         "size": "18MM",
//         "stage": "BASE",
//         "cutter": "PŁYTA",
//         "comment": "express",
//         "customer": "Jankowski",
//         "route": "Śląsk",
//         "completed": false,
//         "quantity": 2,
//         "lastProcessedOn": ISODate("2019-12-22T23:00:00.000+0000"),
//         "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
//     },
//     {
//         "_id": 3,
//         "name": "TW 102",
//         "orderedAt": ISODate("2019-06-09T00:00:00.000+0000"),
//         "color": "CZARNY",
//         "size": "18MM",
//         "stage": "GRINDING",
//         "cutter": "PŁYTA",
//         "comment": "nic",
//         "customer": "Krzychu",
//         "route": "Wrocław",
//         "completed": false,
//         "quantity": 1,
//         "lastProcessedOn": ISODate("2019-12-24T00:00:00.000+0000"),
//         "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
//     },
//     {
//         "_id": 4,
//         "name": "TW 103",
//         "orderedAt": ISODate("2019-06-10T00:00:00.000+0000"),
//         "color": "CZARNY",
//         "size": "18MM",
//         "stage": "PAINTING",
//         "cutter": "PŁYTA",
//         "comment": "",
//         "customer": "Kasa",
//         "route": "Wrocław",
//         "completed": false,
//         "quantity": 1,
//         "lastProcessedOn": ISODate("2019-12-24T00:00:00.000+0000"),
//         "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
//     },
//     {
//         "_id": 5,
//         "name": "TW 104",
//         "orderedAt": ISODate("2019-06-11T00:00:00.000+0000"),
//         "color": "CZARNY",
//         "size": "18MM",
//         "stage": "MILLING",
//         "cutter": "PŁYTA",
//         "comment": "nicasd",
//         "customer": "Michał Kowalski",
//         "route": "Śląsk",
//         "completed": false,
//         "quantity": 1,
//         "lastProcessedOn": ISODate("2019-12-24T00:00:00.000+0000"),
//         "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
//     },
//     {
//         "_id": 6,
//         "name": "TW 105",
//         "orderedAt": ISODate("2019-06-12T00:00:00.000+0000"),
//         "color": "CZARNY",
//         "size": "18MM",
//         "stage": "POLISHING",
//         "cutter": "PŁYTA",
//         "comment": "naaic",
//         "customer": "Meble meblowskie",
//         "route": "Śląsk",
//         "completed": false,
//         "quantity": 1,
//         "lastProcessedOn": ISODate("2019-12-24T00:00:00.000+0000"),
//         "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
//     },
//     {
//         "_id": 7,
//         "name": "TW 106",
//         "orderedAt": ISODate("2019-06-13T00:00:00.000+0000"),
//         "color": "CZARNY",
//         "size": "18MM",
//         "stage": "BASE",
//         "cutter": "PŁYTA",
//         "comment": "nic",
//         "customer": "Jan Golec",
//         "route": "Zakopane",
//         "completed": false,
//         "quantity": 1,
//         "lastProcessedOn": ISODate("2019-12-24T00:00:00.000+0000"),
//         "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
//     },
//     {
//         "_id": 8,
//         "name": "TW 107",
//         "orderedAt": ISODate("2019-06-14T00:00:00.000+0000"),
//         "color": "CZARNY",
//         "size": "18MM",
//         "stage": "GRINDING",
//         "cutter": "PŁYTA",
//         "comment": "nic",
//         "customer": "Dziedzicki",
//         "route": "Wrocław",
//         "completed": false,
//         "quantity": 1,
//         "lastProcessedOn": ISODate("2019-12-24T00:00:00.000+0000"),
//         "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
//     },
//     {
//         "_id": 9,
//         "name": "TW 108",
//         "orderedAt": ISODate("2019-06-15T00:00:00.000+0000"),
//         "color": "CZARNY",
//         "size": "18MM",
//         "stage": "PAINTING",
//         "cutter": "PŁYTA",
//         "comment": "nic",
//         "customer": "Janski",
//         "route": "Śląsk",
//         "completed": false,
//         "quantity": 1,
//         "lastProcessedOn": ISODate("2019-12-24T00:00:00.000+0000"),
//         "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
//     },
//     {
//         "_id": 10,
//         "name": "TW 109",
//         "orderedAt": ISODate("2019-06-15T00:00:00.000+0000"),
//         "color": "CZARNY",
//         "size": "18MM",
//         "stage": "MILLING",
//         "cutter": "PŁYTA",
//         "comment": "nic",
//         "customer": "Kamzy",
//         "route": "Wrocław",
//         "completed": false,
//         "quantity": 1,
//         "lastProcessedOn": ISODate("2019-12-24T00:00:00.000+0000"),
//         "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
//     },
//     {
//         "_id": 11,
//         "name": "TW 110",
//         "orderedAt": ISODate("2019-06-16T00:00:00.000+0000"),
//         "color": "CZARNY",
//         "size": "18MM",
//         "stage": "POLISHING",
//         "cutter": "PŁYTA",
//         "comment": "nic",
//         "customer": "Bastor",
//         "route": "Śląsk",
//         "completed": false,
//         "quantity": 1,
//         "lastProcessedOn": ISODate("2019-12-24T00:00:00.000+0000"),
//         "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
//     },
//     {
//         "_id": 12,
//         "name": "TW 111",
//         "orderedAt": ISODate("2019-06-16T00:00:00.000+0000"),
//         "color": "CZARNY",
//         "size": "18MM",
//         "stage": "BASE",
//         "cutter": "PŁYTA",
//         "comment": "nic",
//         "customer": "Mery",
//         "route": "",
//         "completed": false,
//         "quantity": 1,
//         "lastProcessedOn": ISODate("2019-12-24T00:00:00.000+0000"),
//         "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
//     },
//     {
//         "_id": 13,
//         "name": "TW 112",
//         "orderedAt": ISODate("2019-06-17T00:00:00.000+0000"),
//         "color": "CZARNY",
//         "size": "18MM",
//         "stage": "GRINDING",
//         "cutter": "PŁYTA",
//         "comment": "express; dodatkowe info",
//         "customer": "Ostry",
//         "route": "Milicz",
//         "completed": false,
//         "quantity": 1,
//         "lastProcessedOn": ISODate("2019-12-23T00:00:00.000+0000"),
//         "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
//     },
//     {
//         "_id": 14,
//         "name": "TW 113",
//         "orderedAt": ISODate("2019-06-17T00:00:00.000+0000"),
//         "color": "CZARNY",
//         "size": "18MM",
//         "stage": "PAINTING",
//         "cutter": "PŁYTA",
//         "comment": "express",
//         "customer": "Kamil Kowalski",
//         "route": "Wrocław",
//         "completed": false,
//         "quantity": 1,
//         "lastProcessedOn": ISODate("2019-12-24T00:00:00.000+0000"),
//         "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
//     },
//     {
//         "_id": 15,
//         "name": "TW 114",
//         "orderedAt": ISODate("2019-06-18T00:00:00.000+0000"),
//         "color": "CZARNY",
//         "size": "18MM",
//         "stage": "MILLING",
//         "cutter": "PŁYTA",
//         "comment": "express; jakis klient",
//         "customer": "Jan Kmicic",
//         "route": "Śląsk",
//         "completed": false,
//         "quantity": 1,
//         "lastProcessedOn": ISODate("2019-12-24T00:00:00.000+0000"),
//         "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
//     }
// ]);
// db.front.insertMany([
//     {
//         "_id": 201,
//         "orderId": 2,
//         "height": 100,
//         "width": 200,
//         "quantity": 2,
//         "stage": "BASE",
//         "comment": "casadas",
//         "processings": [
//             {
//                 "stage": "BASE",
//                 "dateTime": ISODate("2019-12-21T23:09:35.563+0000")
//             }
//         ],
//         "amendments": [
//             {
//                 "stage": "BASE",
//                 "dateTime": ISODate("2019-12-21T23:09:55.129+0000")
//             }
//         ],
//         "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
//     },
//     {
//         "_id": 301,
//         "orderId": 3,
//         "height": 100,
//         "width": 200,
//         "quantity": 1,
//         "stage": "GRINDING",
//         "comment": "123",
//         "processings": [
//             {
//                 "stage": "GRINDING",
//                 "dateTime": ISODate("2019-12-23T23:09:36.148+0000")
//             }
//         ],
//         "amendments": [
//             {
//                 "stage": "GRINDING",
//                 "dateTime": ISODate("2019-12-23T23:09:55.138+0000")
//             }
//         ],
//         "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
//     },
//     {
//         "_id": 401,
//         "orderId": 4,
//         "height": 100,
//         "width": 200,
//         "quantity": 1,
//         "stage": "PAINTING",
//         "comment": "aadas",
//         "processings": [
//             {
//                 "stage": "PAINTING",
//                 "dateTime": ISODate("2019-12-22T23:09:36.176+0000")
//             }
//         ],
//         "amendments": [
//             {
//                 "stage": "PAINTING",
//                 "dateTime": ISODate("2019-12-22T23:09:55.143+0000")
//             }
//         ],
//         "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
//     },
//     {
//         "_id": 501,
//         "orderId": 5,
//         "height": 100,
//         "width": 200,
//         "quantity": 1,
//         "stage": "MILLING",
//         "comment": "sasdasd",
//         "processings": [
//             {
//                 "stage": "MILLING",
//                 "dateTime": ISODate("2019-12-21T23:09:36.197+0000")
//             }
//         ],
//         "amendments": [
//             {
//                 "stage": "MILLING",
//                 "dateTime": ISODate("2019-12-21T23:09:55.146+0000")
//             }
//         ],
//         "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
//     },
//     {
//         "_id": 601,
//         "orderId": 6,
//         "height": 100,
//         "width": 200,
//         "quantity": 1,
//         "stage": "POLISHING",
//         "comment": "komentarz yk",
//         "processings": [
//             {
//                 "stage": "POLISHING",
//                 "dateTime": ISODate("2019-12-23T23:09:36.223+0000")
//             }
//         ],
//         "amendments": [
//             {
//                 "stage": "POLISHING",
//                 "dateTime": ISODate("2019-12-23T23:09:55.152+0000")
//             }
//         ],
//         "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
//     },
//     {
//         "_id": 701,
//         "orderId": 7,
//         "height": 100,
//         "width": 200,
//         "quantity": 1,
//         "stage": "BASE",
//         "comment": "2",
//         "processings": [
//             {
//                 "stage": "BASE",
//                 "dateTime": ISODate("2019-12-22T23:09:36.244+0000")
//             }
//         ],
//         "amendments": [
//             {
//                 "stage": "BASE",
//                 "dateTime": ISODate("2019-12-22T23:09:55.157+0000")
//             }
//         ],
//         "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
//     },
//     {
//         "_id": 801,
//         "orderId": 8,
//         "height": 100,
//         "width": 200,
//         "quantity": 1,
//         "stage": "GRINDING",
//         "comment": "123",
//         "processings": [
//             {
//                 "stage": "GRINDING",
//                 "dateTime": ISODate("2019-12-21T23:09:36.264+0000")
//             }
//         ],
//         "amendments": [
//             {
//                 "stage": "GRINDING",
//                 "dateTime": ISODate("2019-12-21T23:09:55.161+0000")
//             }
//         ],
//         "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
//     },
//     {
//         "_id": 901,
//         "orderId": 9,
//         "height": 100,
//         "width": 200,
//         "quantity": 1,
//         "stage": "PAINTING",
//         "comment": "sad 3",
//         "processings": [
//             {
//                 "stage": "PAINTING",
//                 "dateTime": ISODate("2019-12-23T23:09:36.291+0000")
//             }
//         ],
//         "amendments": [
//             {
//                 "stage": "PAINTING",
//                 "dateTime": ISODate("2019-12-23T23:09:55.167+0000")
//             }
//         ],
//         "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
//     },
//     {
//         "_id": 1001,
//         "orderId": 10,
//         "height": 100,
//         "width": 200,
//         "quantity": 1,
//         "stage": "MILLING",
//         "comment": "3",
//         "processings": [
//             {
//                 "stage": "MILLING",
//                 "dateTime": ISODate("2019-12-22T23:09:36.321+0000")
//             }
//         ],
//         "amendments": [
//             {
//                 "stage": "MILLING",
//                 "dateTime": ISODate("2019-12-22T23:09:55.173+0000")
//             }
//         ],
//         "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
//     },
//     {
//         "_id": 1101,
//         "orderId": 11,
//         "height": 100,
//         "width": 200,
//         "quantity": 1,
//         "stage": "POLISHING",
//         "comment": "",
//         "processings": [
//             {
//                 "stage": "POLISHING",
//                 "dateTime": ISODate("2019-12-21T23:09:36.343+0000")
//             }
//         ],
//         "amendments": [
//             {
//                 "stage": "POLISHING",
//                 "dateTime": ISODate("2019-12-21T23:09:55.177+0000")
//             }
//         ],
//         "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
//     },
//     {
//         "_id": 1201,
//         "orderId": 12,
//         "height": 100,
//         "width": 200,
//         "quantity": 1,
//         "stage": "BASE",
//         "comment": "nic",
//         "processings": [
//             {
//                 "stage": "BASE",
//                 "dateTime": ISODate("2019-12-23T23:09:36.368+0000")
//             }
//         ],
//         "amendments": [
//             {
//                 "stage": "BASE",
//                 "dateTime": ISODate("2019-12-23T23:09:55.180+0000")
//             }
//         ],
//         "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
//     },
//     {
//         "_id": 1301,
//         "orderId": 13,
//         "height": 100,
//         "width": 200,
//         "quantity": 1,
//         "stage": "GRINDING",
//         "comment": "niee",
//         "processings": [
//             {
//                 "stage": "GRINDING",
//                 "dateTime": ISODate("2019-12-22T23:09:36.390+0000")
//             }
//         ],
//         "amendments": [
//             {
//                 "stage": "GRINDING",
//                 "dateTime": ISODate("2019-12-22T23:09:55.187+0000")
//             }
//         ],
//         "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
//     },
//     {
//         "_id": 1401,
//         "orderId": 14,
//         "height": 100,
//         "width": 200,
//         "quantity": 1,
//         "stage": "PAINTING",
//         "comment": "wcale",
//         "processings": [
//             {
//                 "stage": "PAINTING",
//                 "dateTime": ISODate("2019-12-21T23:09:36.411+0000")
//             }
//         ],
//         "amendments": [
//             {
//                 "stage": "PAINTING",
//                 "dateTime": ISODate("2019-12-21T23:09:55.191+0000")
//             }
//         ],
//         "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
//     },
//     {
//         "_id": 1501,
//         "orderId": 15,
//         "height": 100,
//         "width": 200,
//         "quantity": 1,
//         "stage": "MILLING",
//         "comment": "grubo",
//         "processings": [
//             {
//                 "stage": "MILLING",
//                 "dateTime": ISODate("2019-12-23T23:09:36.429+0000")
//             }
//         ],
//         "amendments": [
//             {
//                 "stage": "MILLING",
//                 "dateTime": ISODate("2019-12-23T23:09:55.194+0000")
//             }
//         ],
//         "_class": "com.frontwit.barcodeapp.administration.processing.front.infrastructure.persistence.FrontEntity"
//     }
// ]);

db.order.insertMany([
    {
        "_id": 1,
        "name": "TW 101",
        "orderedAt": ISODate("2019-06-07T22:00:00.000+0000"),
        "color": "CZARNY",
        "size": "18MM",
        "stage": "BASE",
        "cutter": "PŁYTA",
        "comment": "express",
        "customer": "Jankowski",
        "route": "Śląsk",
        "completed": false,
        "packed": false,
        "quantity": 2,
        "lastProcessedOn": ISODate("2019-12-22T23:00:00.000+0000"),
        "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
    },
    {
        "_id": 2,
        "name": "TW 201",
        "orderedAt": ISODate("2019-06-07T22:00:00.000+0000"),
        "color": "BIAŁY",
        "size": "18MM",
        "stage": "BASE",
        "cutter": "PŁYTA",
        "comment": "express",
        "customer": "Kowal",
        "route": "Wrocław",
        "completed": false,
        "packed": true,
        "quantity": 2,
        "lastProcessedOn": ISODate("2019-12-22T23:00:00.000+0000"),
        "_class": "com.frontwit.barcodeapp.administration.infrastructure.database.OrderEntity"
    }
]);
db.front.insertMany([
    {
        "_id": 101,
        "orderId": 1,
        "height": 100,
        "width": 200,
        "quantity": 2,
        "stage": "BASE",
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
        "stage": "BASE",
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