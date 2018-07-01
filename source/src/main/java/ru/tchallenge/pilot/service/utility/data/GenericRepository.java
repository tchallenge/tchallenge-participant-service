package ru.tchallenge.pilot.service.utility.data;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import ru.tchallenge.pilot.service.configuration.PersistenceConfiguration;
import ru.tchallenge.pilot.service.context.GenericApplicationComponent;

public abstract class GenericRepository extends GenericApplicationComponent {

    private MongoCollection<Document> documents;

    public long count() {
        return this.documents.count();
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

    @Override
    public void init() {
        super.init();
        PersistenceConfiguration persistenceConfiguration = getComponent(PersistenceConfiguration.class);
        this.documents = persistenceConfiguration.getDocumentCollection(getCollectionName());
    }

    protected MongoCollection<Document> documents() {
        return documents;
    }

    protected abstract String getCollectionName();
}
