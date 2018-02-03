package ru.tchallenge.participant.service.utility.data;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.Date;

public class DocumentWrapper implements IdAware {

    private static final String ATTRIBUTE_ID = "_id";
    private static final String ATTRIBUTE_CREATED_AT = "createdAt";
    private static final String ATTRIBUTE_LAST_MODIFIED_AT = "lastModifiedAt";

    protected final Document document;

    public DocumentWrapper() {
        this.document = new Document();
    }

    public DocumentWrapper(final Id id) {
        if (id == null) {
            throw new NullPointerException("Document ID is missing");
        }
        this.document = attachId(new Document(), id);
    }

    public DocumentWrapper(final Document document) {
        if (document == null) {
            throw new NullPointerException("Document is missing");
        }
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    @Override
    public Id getId() {
        return retrieveId(document);
    }

    public Instant getCreatedAt() {
        return document.getDate(ATTRIBUTE_CREATED_AT).toInstant();
    }

    public Instant getLastModifiedAt() {
        return document.getDate(ATTRIBUTE_LAST_MODIFIED_AT).toInstant();
    }

    public DocumentWrapper deleteFrom(final MongoCollection<Document> collection) {
        collection.deleteOne(getId().toFilter());
        return this;
    }

    public DocumentWrapper insertInto(final MongoCollection<Document> collection) {
        final Date now = Date.from(Instant.now());
        document.put(ATTRIBUTE_CREATED_AT, now);
        document.put(ATTRIBUTE_LAST_MODIFIED_AT, now);
        if (retrieveId(document) == null) {
            attachId(document, new Id());
        }
        collection.insertOne(document);
        return this;
    }

    public DocumentWrapper replaceWithin(final MongoCollection<Document> collection) {
        final Date now = Date.from(Instant.now());
        document.put(ATTRIBUTE_LAST_MODIFIED_AT, now);
        collection.replaceOne(getId().toFilter(), document);
        return this;
    }

    private Document attachId(final Document document, final Id id) {
        document.put(ATTRIBUTE_ID, id.toObjectId());
        return document;
    }

    private Id retrieveId(final Document document) {
        final ObjectId objectId = document.getObjectId(ATTRIBUTE_ID);
        return objectId != null ? new Id(objectId) : null;
    }
}
