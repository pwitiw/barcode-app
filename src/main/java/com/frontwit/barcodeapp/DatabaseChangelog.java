package com.frontwit.barcodeapp;

import com.frontwit.barcodeapp.datatype.Barcode;
import com.frontwit.barcodeapp.datatype.Stage;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.google.common.collect.Lists;
import com.mongodb.*;

import java.util.Date;
import java.util.List;

import static com.frontwit.barcodeapp.logic.BarcodeGenerator.BARCODE_ID;
import static com.frontwit.barcodeapp.logic.BarcodeGenerator.BARCODE_INIT_VALUE;

@ChangeLog
public class DatabaseChangelog {

    private static final String ID_FIELD = "_id";
    private static final String FOREIGN_KEY = "$id";
    private static final String REFERENCE = "$ref";
    private static final String STAGE= "stage";

    @ChangeSet(order = "001", id = "Barcode init value", author = "Admin")
    public void createCounters(DB db) {
        DBCollection counterCollection = db.getCollection("counter");
        DBObject object = new BasicDBObject()
                .append(ID_FIELD, BARCODE_ID)
                .append("value", BARCODE_INIT_VALUE);
        counterCollection.save(object);
    }

    @ChangeSet(order = "002", id = "Test data", author = "Admin")
    public void createTestOrder(DB db) {

        DBCollection orderCollection = db.getCollection("order");
        DBCollection workerCollection = db.getCollection("worker");
        DBCollection routeCollection = db.getCollection("route");

        //WORKERS
        DBObject worker1 = createWorkerAsDbObject("Frezarz", "Frezarz", Barcode.valueOf(1));
        DBObject worker2 = createWorkerAsDbObject("Podklad", "Podklad", Barcode.valueOf(2));
        DBObject worker3 = createWorkerAsDbObject("Pakowanie", "Pakowanie", Barcode.valueOf(3));
        DBObject worker4 = createWorkerAsDbObject("Kurier", "Kurier", Barcode.valueOf(4));
        workerCollection.save(worker1);
        workerCollection.save(worker2);
        workerCollection.save(worker3);
        workerCollection.save(worker4);

        //ROUTES
        DBObject route1 = createRoute("Trasa 1");
        DBObject route2 = createRoute("Trasa 2");
        routeCollection.save(route1);
        routeCollection.save(route2);

        //PROCESSES
        DBObject process1 = createProcess(worker1, Stage.MILLING);
        DBObject process2 = createProcess(worker2, Stage.POLISHING);
        DBObject process3 = createProcess(worker3, Stage.PACKING);
        DBObject process4 = createProcess(worker4, Stage.DELIVERD);

        //COMPONENTS
        DBObject component1 = createComponentAsDBObject(Barcode.valueOf(123), 1, 2, true, Lists.newArrayList(process1, process2, process3));
        DBObject component2 = createComponentAsDBObject(Barcode.valueOf(345), 3, 4, false, Lists.newArrayList(process1, process2));
        DBObject component3 = createComponentAsDBObject(Barcode.valueOf(789), 5, 6, true, Lists.newArrayList(process1, process2));
        DBObject component4 = createComponentAsDBObject(Barcode.valueOf(1213), 7, 8, false, Lists.newArrayList(process1));
        DBObject component5 = createComponentAsDBObject(Barcode.valueOf(1415), 9, 10, false, Lists.newArrayList(process1, process2));
        DBObject component6 = createComponentAsDBObject(Barcode.valueOf(1617), 11, 12, false, Lists.newArrayList(process1));
        DBObject component7 = createComponentAsDBObject(Barcode.valueOf(1819), 13, 14, true, Lists.newArrayList(process1, process2, process3,process4));

        // ORDER 1
        DBObject order1 = createOrder("Zamówienie 1", Lists.newArrayList(component1, component2, component3), route1, 2);
        DBObject order2 = createOrder("Zamówienie 2", Lists.newArrayList(component4, component5), route1, 0);
        DBObject order3 = createOrder("Zamówienie 3", Lists.newArrayList(component6, component7), route2, 1);
        orderCollection.save(order1);
        orderCollection.save(order2);
        orderCollection.save(order3);
    }


    private DBObject createOrder(String name, List<DBObject> componentList, DBObject route, int damagedComponentsAmount) {
        BasicDBList components = new BasicDBList();
        components.addAll(componentList);

        return new BasicDBObject()
                .append("name", name)
                .append("components", components)
                .append("route", createReferenceObject("route", route))
                .append("componentAmount", componentList.size())
                .append("orderedAt", new Date())
                .append("damagedComponentsAmount", damagedComponentsAmount)
                .append(STAGE, componentList.get(0).get(STAGE));

    }

    private DBObject createRoute(String name) {
        return new BasicDBObject()
                .append("name", name);
    }

    private DBObject createComponentAsDBObject(Barcode barcode, int height, int width, boolean damaged, List<DBObject> processes) {
        BasicDBList processingHistory = new BasicDBList();
        processingHistory.addAll(processes);
        return new BasicDBObject()
                .append("barcode", createBarcode(barcode))
                .append("height", height)
                .append("width", width)
                .append("lastModification", new Date())
                .append("processingHistory", processingHistory)
                .append("damaged", damaged);
    }

    private DBObject createProcess(DBObject worker, Stage stage) {
        return new BasicDBObject()
                .append("worker", createReferenceObject("worker", worker))
                .append(STAGE, stage.toString())
                .append("date", new Date());
    }

    private DBObject createWorkerAsDbObject(String name, String lastName, Barcode barcode) {
        return new BasicDBObject()
                .append("firstName", name)
                .append("lastName", lastName)
                .append("barcode", createBarcode(barcode));
    }

    private DBObject createBarcode(Barcode barcode) {
        return new BasicDBObject()
                .append("value", barcode.getValue().longValue());
    }

    private DBObject createReferenceObject(String collectionName, DBObject object) {
        return new BasicDBObject()
                .append(REFERENCE, collectionName)
                .append(FOREIGN_KEY, object.get(ID_FIELD));
    }
}