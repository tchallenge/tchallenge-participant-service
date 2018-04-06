package ru.tchallenge.participant.service.domain.event;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;

import ru.tchallenge.participant.service.security.authentication.Authentication;
import ru.tchallenge.participant.service.security.authentication.AuthenticationContext;
import ru.tchallenge.participant.service.security.authentication.AuthenticationContextBean;
import ru.tchallenge.participant.service.utility.data.Id;

public final class EventManager {

    public static final EventManager INSTANCE = new EventManager();

    private final AuthenticationContext authenticationContext = AuthenticationContextBean.INSTANCE;
    private final EventProjector eventProjector = EventProjector.INSTANCE;
    private final EventRepository eventRepository = EventRepository.INSTANCE;

    private EventManager() {

    }

    public Event retrieveById(final Id id) {
        authentication();
        final EventDocument document = eventRepository.findById(id);
        if (document == null) {
            throw new RuntimeException("Event is not found");
        }
        return eventProjector.event(document);
    }

    public EventSearchResult retrieveSearchResult(final EventSearchInvoice invoice) {
        authentication();
        final List<EventDocument> documents = eventRepository.find(invoice);
        return EventSearchResult.builder()
                .items(ImmutableList.copyOf(mapSearchItems(documents)))
                .total(42)
                .build();
    }

    private List<Event> mapSearchItems(final List<EventDocument> documents) {
        return documents
                .stream()
                .map(eventProjector::eventShort)
                .collect(Collectors.toList());
    }

    private Authentication authentication() {
        return authenticationContext.getAuthentication();
    }
}
