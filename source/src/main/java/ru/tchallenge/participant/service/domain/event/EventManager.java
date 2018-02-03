package ru.tchallenge.participant.service.domain.event;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.bson.Document;
import ru.tchallenge.participant.service.security.authentication.AuthenticationContext;

import java.util.List;

public final class EventManager {

    public static final EventManager INSTANCE = new EventManager();

    public Event retrieveById(final String id) {
        authenticated();
        final Document eventDocument = eventRepository.findById(id);
        return eventProjector.intoEvent(eventDocument);
    }

    public EventSearchResult retrieveSearchResult(final EventSearchInvoice invoice) {
        authenticated();
        final Document filter = new Document().append("textcode", invoice.getFilter().getTextcode());
        final List<Event> events = eventRepository
                .find(filter)
                .skip(invoice.getOffset())
                .limit(invoice.getLimit())
                .map(eventProjector::intoEvent)
                .into(Lists.newArrayList());
        return EventSearchResult.builder()
                .items(ImmutableList.copyOf(events))
                .total(42)
                .build();
    }

    private String authenticated() {
        return AuthenticationContext.getAuthentication().getAccountId();
    }

    private final EventProjector eventProjector = EventProjector.INSTANCE;
    private final EventRepository eventRepository = EventRepository.INSTANCE;

    private EventManager() {

    }
}
