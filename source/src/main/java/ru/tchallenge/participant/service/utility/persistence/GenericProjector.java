package ru.tchallenge.participant.service.utility.persistence;

import org.bson.Document;
import ru.tchallenge.participant.service.utility.data.IdAware;

public abstract class GenericProjector {

    public IdAware justId(final Document document) {
        return ObjectIdWrapper.fromDocument(document).justId();
    }
}
