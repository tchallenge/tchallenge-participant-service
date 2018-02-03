package ru.tchallenge.participant.service.utility.persistence;

import org.bson.Document;
import ru.tchallenge.participant.service.utility.data.IdAware;

import java.util.Objects;

public class DocumentWrapper implements IdAware {

    public static DocumentWrapper fromDocument(final Document document) {
        return new DocumentWrapper(document);
    }

    private final Document document;

    public DocumentWrapper(final Document document) {
        Objects.requireNonNull(document);
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    @Override
    public String getId() {
        return toId().getId();
    }

    public ObjectIdWrapper toId() {
        return ObjectIdWrapper.fromDocument(document);
    }
}
