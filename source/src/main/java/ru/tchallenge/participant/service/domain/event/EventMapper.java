package ru.tchallenge.participant.service.domain.event;

import org.bson.Document;

public final class EventMapper {

    public static final EventMapper INSTANCE = new EventMapper();

    public Event intoEvent(final Document document) {
        return Event.builder()
                .id(document.getObjectId("_id").toHexString())
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
                .id(document.getObjectId("_id").toHexString())
                .textcode(document.getString("textcode"))
                .caption(document.getString("caption"))
                .status(document.getString("status"))
                .build();
    }

    private EventMapper() {

    }
}
