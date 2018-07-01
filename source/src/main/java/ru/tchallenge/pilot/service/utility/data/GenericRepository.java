package ru.tchallenge.pilot.service.utility.data;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import ru.tchallenge.pilot.service.PersistenceManager;

public abstract class GenericRepository {

    private final MongoCollection<Document> documents;

    public GenericRepository(final String collectionName) {
        this.documents = PersistenceManager.getDocumentCollection(collectionName);
    }

    public void delete(final DocumentWrapper documentWrapper) {
        documentWrapper.deleteFrom(documents);
    }

    public void insert(final DocumentWrapper documentWrapper) {
        documentWrapper.insertInto(documents);
    }

    public DocumentWrapper findById(final Id id) {
        final Document document = documents
                .find()
                .filter(id.toFilter())
                .first();
        return document != null ? new DocumentWrapper(document) : null;
    }

    public void replace(final DocumentWrapper documentWrapper) {
        documentWrapper.replaceWithin(documents);
    }

    protected MongoCollection<Document> documents() {
        return documents;
    }
}
