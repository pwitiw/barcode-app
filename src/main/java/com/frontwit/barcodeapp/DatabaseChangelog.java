package com.frontwit.barcodeapp;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "Counters", author = "Admin")
    public void createCounters(DB db) {

        DBCollection counters = db.getCollection("counter");
        DBObject object = new BasicDBObject()
                .append("_id", "order")
                .append("value", 0);
        counters.save(object);
    }

}