package com.example.passwordmanager.migration;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.bson.types.ObjectId;

@ChangeLog
public class DatabaseChangelog {

    private static final String COLLECTION_NAME = "passwords";

    @ChangeSet(order = "001", id = "createAdminPassword", author = "Sergio Banegas Cortijo")
    public void createAdminPassword(DB db) {
        DBCollection passwords = db.getCollection(COLLECTION_NAME);
        passwords.insert(BasicDBObjectBuilder
                .start()
                .add("_id", new ObjectId("590f86d92449343841cc2c3f"))
                .add("password", "$2a$10$hBY9enbm.jSfg6sbwOCB9OAcSflEflCnLWvdiQMLKTnqVTXaDmy6q")
                .get());
    }


}