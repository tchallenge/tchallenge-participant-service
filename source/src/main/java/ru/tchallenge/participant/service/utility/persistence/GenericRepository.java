package ru.tchallenge.participant.service.utility.persistence;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import ru.tchallenge.participant.service.PersistenceManager;

import java.time.Instant;
import java.util.Date;

public abstract class GenericRepository {

    private final MongoCollection<Document> documents;

    public GenericRepository(final String collectionName) {
        this.documents = PersistenceManager.getDocumentCollection(collectionName);
    }

    public void delete(final Document document) {
        final Document filter = ObjectIdWrapper.fromDocument(document).toFilter();
        documents.deleteOne(filter);
    }

    public void insert(final Document document) {
        final Instant instant = Instant.now();
        ObjectIdWrapper.newId().attach(document);
        document.put("createdAt", Date.from(instant));
        document.put("lastModifiedAt", Date.from(instant));
        documents.insertOne(document);
    }

    public Document findById(final ObjectId id) {
        return documents
                .find()
                .filter(ObjectIdWrapper.fromObjectId(id).toFilter())
                .first();
    }

    public Document findById(final String id) {
        return findById(new ObjectId(id));
    }

    public void update(final ObjectId id, final Document update) {
        final Instant instant = Instant.now();
        update.put("lastModifiedAt", Date.from(instant));
        documents.updateOne(ObjectIdWrapper.fromObjectId(id).toFilter(), update);
    }

    protected MongoCollection<Document> documents() {
        return documents;
    }
}
