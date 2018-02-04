package ru.tchallenge.participant.service.utility.data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

public class DocumentWrapper implements IdAware {

    protected static final String ATTRIBUTE_ID = "_id";
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
        return retrieveId(ATTRIBUTE_ID);
    }

    public Instant getCreatedAt() {
        return retrieveInstant(ATTRIBUTE_CREATED_AT);
    }

    public Instant getLastModifiedAt() {
        return retrieveInstant(ATTRIBUTE_LAST_MODIFIED_AT);
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

    protected Boolean retrieveBoolean(final String attribute) {
        return document.getBoolean(attribute);
    }

    protected Id retrieveId(final String attribute) {
        return new Id(document.getObjectId(attribute));
    }

    protected Instant retrieveInstant(final String attribute) {
        return document.getDate(attribute).toInstant();
    }

    protected Integer retrieveInteger(final String attribute) {
        return document.getInteger(attribute);
    }

    protected <T> List<T> retrieveListOfDocuments(final String attribute, final Function<Document, T> mapper) {
        final List<Document> list = cast(document.get(attribute));
        if (list == null) {
            return new ArrayList<>();
        }
        return list
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    protected List<Id> retrieveListOfIds(final String attribute) {
        final List<ObjectId> list = cast(document.get(attribute));
        if (list == null) {
            return new ArrayList<>();
        }
        return list
                .stream()
                .map(Id::new)
                .collect(Collectors.toList());
    }

    protected List<String> retrieveListOfStrings(final String attribute) {
        final List<String> list = cast(document.get(attribute));
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    protected String retrieveString(final String attribute) {
        return document.getString(attribute);
    }

    private static Document attachId(final Document document, final Id id) {
        document.put(ATTRIBUTE_ID, id.toObjectId());
        return document;
    }

    protected static Set<String> setOfStrings(final Set<? extends Enum> enums) {
        return enums
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    protected static List<String> listOfStrings(final List<? extends Enum> enums) {
        return enums
                .stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    private static Id retrieveId(final Document document) {
        final ObjectId objectId = document.getObjectId(ATTRIBUTE_ID);
        return objectId != null ? new Id(objectId) : null;
    }

    @SuppressWarnings("unchecked")
    private static <T> T cast(final Object object) {
        return (T) object;
    }
}
