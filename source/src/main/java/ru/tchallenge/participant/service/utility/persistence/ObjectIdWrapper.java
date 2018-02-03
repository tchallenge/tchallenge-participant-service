package ru.tchallenge.participant.service.utility.persistence;

import org.bson.Document;
import org.bson.types.ObjectId;
import ru.tchallenge.participant.service.utility.data.IdAware;

import java.util.Objects;

public final class ObjectIdWrapper implements IdAware {

    public static final String ATTRIBUTE = "_id";

    public static ObjectIdWrapper fromDocument(final Document document) {
        return new ObjectIdWrapper(document.getObjectId(ATTRIBUTE));
    }

    public static ObjectIdWrapper fromHex(final String hex) {
        return new ObjectIdWrapper(new ObjectId(hex));
    }

    public static ObjectIdWrapper fromObjectId(final ObjectId objectId) {
        return new ObjectIdWrapper(objectId);
    }

    public static ObjectIdWrapper newId() {
        return new ObjectIdWrapper(new ObjectId());
    }

    private final ObjectId objectId;

    public ObjectIdWrapper(final ObjectId objectId) {
        Objects.requireNonNull(objectId);
        this.objectId = objectId;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    @Override
    public String getId() {
        return toHex();
    }

    public Document attach(final Document document) {
        document.put(ATTRIBUTE, objectId);
        return document;
    }

    public Document toFilter() {
        return attach(new Document());
    }

    public String toHex() {
        return objectId.toHexString();
    }
}
