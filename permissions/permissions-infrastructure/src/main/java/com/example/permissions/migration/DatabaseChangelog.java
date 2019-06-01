package com.example.permissions.migration;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.bson.types.ObjectId;

@ChangeLog
public class DatabaseChangelog {

    private static final String COLLECTION_NAME = "user_roles";

    @ChangeSet(order = "001", id = "createAdminRole", author = "Sergio Banegas Cortijo")
    public void createAdminRole(DB db) {
        DBCollection mycollection = db.getCollection(COLLECTION_NAME);
        BasicDBObject doc = new BasicDBObject()
                .append("_id", new ObjectId("590f86d92449343841cc2c3f"))
                .append("role", "ADMIN");
        mycollection.insert(doc);
    }

}