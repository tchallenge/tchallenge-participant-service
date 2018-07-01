package ru.tchallenge.pilot.service.configuration;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedConfiguration;

@ManagedConfiguration
public class PersistenceConfiguration extends GenericApplicationComponent {

    private MongoDatabase database;

    public MongoCollection<Document> getDocumentCollection(String name) {
        return database.getCollection(name);
    }

    @Override
    public void init() {
        super.init();
        String mongodbHost = System.getenv("TCHALLENGE_MONGODB_HOST");
        String mongodbPort = System.getenv("TCHALLENGE_MONGODB_PORT");
        String mongodbDatabase = System.getenv("TCHALLENGE_MONGODB_DATABASE");
        MongoClient mongoClient = new MongoClient(mongodbHost, Integer.parseInt(mongodbPort));
        database = mongoClient.getDatabase(mongodbDatabase);
    }
}
