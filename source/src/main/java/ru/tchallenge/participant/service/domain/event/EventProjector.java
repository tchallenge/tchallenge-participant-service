package ru.tchallenge.participant.service.domain.event;

import org.bson.Document;
import ru.tchallenge.participant.service.utility.persistence.DocumentWrapper;
import ru.tchallenge.participant.service.utility.persistence.GenericProjector;

public final class EventProjector extends GenericProjector {

    public static final EventProjector INSTANCE = new EventProjector();

    public Event intoEvent(final Document document) {
        return Event.builder()
                .id(DocumentWrapper.fromDocument(document).getId())
                .textcode(document.getString("textcode"))
                .caption(document.getString("caption"))
                .description(document.getString("description"))
                .greeting(document.getString("greeting"))
                .validFrom(document.getDate("validFrom").toInstant())
                .validUntil(document.getDate("validUntil").toInstant())
                .status(document.getString("status"))
                .build();
    }

    public Event intoEventShort(final Document document) {
        return Event.builder()
                .id(DocumentWrapper.fromDocument(document).getId())
                .textcode(document.getString("textcode"))
                .caption(document.getString("caption"))
                .status(document.getString("status"))
                .build();
    }

    private EventProjector() {

    }
}
