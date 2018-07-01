package ru.tchallenge.pilot.service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public final class PersistenceManager {

    public static MongoCollection<Document> getDocumentCollection(final String name) {
        return DATABASE.getCollection(name);
    }

    private static final MongoDatabase DATABASE;
    private static final String DATABASE_NAME = "tchallenge-participant";
    private static final String DATABASE_HOST = "localhost";
    private static final Integer DATABASE_PORT = 27017;

    static {
        MongoClient mongoClient = new MongoClient(DATABASE_HOST, DATABASE_PORT);
        DATABASE = mongoClient.getDatabase(DATABASE_NAME);
    }

    private PersistenceManager() {

    }
}
