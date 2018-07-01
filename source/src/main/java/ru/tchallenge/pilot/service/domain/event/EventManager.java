package ru.tchallenge.pilot.service.domain.event;

import java.util.List;
import java.util.stream.Collectors;

import spark.Request;

import com.google.common.collect.ImmutableList;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.security.authentication.Authentication;
import ru.tchallenge.pilot.service.security.authentication.AuthenticationRequestContext;
import ru.tchallenge.pilot.service.utility.data.Id;

@ManagedComponent
public class EventManager extends GenericApplicationComponent {

    private EventRepository eventRepository;
    private EventProjector eventProjector;

    @Override
    public void init() {
        super.init();
        this.eventProjector = getComponent(EventProjector.class);
        this.eventRepository = getComponent(EventRepository.class);
    }

    public Event retrieveById(Request request, Id id) {
        authentication(request);
        final EventDocument document = eventRepository.findById(id);
        if (document == null) {
            throw new RuntimeException("Event is not found");
        }
        return eventProjector.event(document);
    }

    public EventSearchResult retrieveSearchResult(Request request, EventSearchInvoice invoice) {
        authentication(request);
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

    private Authentication authentication(Request request) {
        return new AuthenticationRequestContext(request).getAuthentication();
    }
}
