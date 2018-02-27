package com.frontwit.barcodeapp;

import com.frontwit.barcodeapp.entity.Order;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "New Entry", author = "System")
    public void script(DB db) {

        DBCollection mycollection = db.getCollection("order");
        BasicDBObject doc = new BasicDBObject()
                .append("test", "1")
                .append("test1", "1")
                .append("test2", "1");
        mycollection.insert(doc);
    }
}