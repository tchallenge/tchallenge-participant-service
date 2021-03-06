package ru.tchallenge.pilot.service.domain.event;

import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.data.GenericProjector;

@ManagedComponent
public class EventProjector extends GenericProjector {

    public Event event(final EventDocument document) {
        return Event.builder()
                .id(document.getId())
                .caption(document.getCaption())
                .description(document.getDescription())
                .greeting(document.getGreeting())
                .notifications(immutableList(document.getNotifications()))
                .maturities(immutableList(document.getMaturities()))
                .permalink(document.getCaption())
                .specializationIds(immutableList(document.getSpecializationIds()))
                .status(document.getStatus())
                .validFrom(document.getValidFrom())
                .validUntil(document.getValidUntil())
                .build();
    }

    public Event eventShort(final EventDocument document) {
        return Event.builder()
                .id(document.getId())
                .permalink(document.getPermalink())
                .caption(document.getCaption())
                .status(document.getStatus())
                .build();
    }
}
