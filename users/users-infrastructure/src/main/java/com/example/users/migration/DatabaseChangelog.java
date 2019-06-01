package com.example.users.migration;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.bson.types.ObjectId;

@ChangeLog
public class DatabaseChangelog {

    private static final String COLLECTION_NAME = "users";

    @ChangeSet(order = "001", id = "createAdminUser", author = "Sergio Banegas Cortijo")
    public void createAdminUser(DB db) {
        DBCollection mycollection = db.getCollection(COLLECTION_NAME);
        BasicDBObject doc = new BasicDBObject()
                .append("_id", new ObjectId("590f86d92449343841cc2c3f"))
                .append("email", "admin@gmail.com")
                .append("name", "Sergio")
                .append("phone", "+34 888 888 888")
                .append("deleted", false);
        mycollection.insert(doc);
    }

}
