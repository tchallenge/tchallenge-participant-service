package ru.tchallenge.participant.service.domain.workbook;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import static ru.tchallenge.participant.service.PersistenceManager.collection;

public final class WorkbookRepository {

    public static final WorkbookRepository INSTANCE = new WorkbookRepository();

    private final MongoCollection<Document> collection = collection("workbooks");

    private WorkbookRepository() {

    }

    public Document findById(final String id) {
        return collection
                .find()
                .filter(idFilter(id))
                .first();
    }

    public Document save(final Document document) {
        final ObjectId id = id(document);
        if (id == null) {
            collection.insertOne(document);
        }  else {
            collection.replaceOne(idFilter(id), document);
        }
        return document;
    }

    private ObjectId id(final Document document) {
        return document.getObjectId("_id");
    }

    private Document idFilter(final ObjectId id) {
        return new Document("_id", id);
    }

    private Document idFilter(final String id) {
        return new Document("_id", new ObjectId(id));
    }
}
