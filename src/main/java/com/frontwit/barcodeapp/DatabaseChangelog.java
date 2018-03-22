package com.frontwit.barcodeapp;

import com.beust.jcommander.internal.Lists;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.*;

import java.util.Date;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "Counters", author = "Admin")
    public void createCounters(DB db) {
        updateCounter(db, 0);

    }

    @ChangeSet(order = "001", id = "Test data", author = "Admin")
    public void createTestOrder(DB db) {
        int id = 1;
        DBCollection orders = db.getCollection("order");
        DBObject c1 = componentAsDBObject(123, 1, 2);
        DBObject c2 = componentAsDBObject(345, 3, 4);
        BasicDBList components = new BasicDBList();
        components.addAll(Lists.newArrayList(c1, c2));
        DBObject object = new BasicDBObject()
                .append("_id", id)
                .append("name", "TW100")
                .append("components", components);
        orders.save(object);
        updateCounter(db, id);
    }

    private void updateCounter(DB db, int value) {
        DBCollection counters = db.getCollection("counter");
        DBObject object = new BasicDBObject()
                .append("_id", "order")
                .append("value", value);
        counters.save(object);
    }

    private DBObject componentAsDBObject(int barcode, int height, int width) {
        return new BasicDBObject()
                .append("barcode", new BasicDBObject().append("value", barcode))
                .append("length", height)
                .append("width", width)
                .append("lastModification", new Date())
                .append("processingHistory",new BasicDBList())
                .append("damaged",false);
    }

}