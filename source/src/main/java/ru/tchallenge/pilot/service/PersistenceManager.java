package ru.tchallenge.pilot.service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class PersistenceManager {

    public static MongoCollection<Document> getDocumentCollection(String name) {
        return DATABASE.getCollection(name);
    }

    private static MongoDatabase DATABASE;

    static {
        String mongodbHost = System.getenv("TCHALLENGE_MONGODB_HOST");
        String mongodbPort = System.getenv("TCHALLENGE_MONGODB_PORT");
        String mongodbDatabase = System.getenv("TCHALLENGE_MONGODB_DATABASE");
        MongoClient mongoClient = new MongoClient(mongodbHost, Integer.parseInt(mongodbPort));
        DATABASE = mongoClient.getDatabase(mongodbDatabase);
    }

    private PersistenceManager() {

    }
}
