package ru.tchallenge.participant.service.domain.event;

import java.util.ArrayList;
import java.util.List;

import ru.tchallenge.participant.service.utility.data.DocumentWrapper;
import ru.tchallenge.participant.service.utility.data.GenericRepository;
import ru.tchallenge.participant.service.utility.data.Id;

public final class EventRepository extends GenericRepository {

    public static final EventRepository INSTANCE = new EventRepository();

    private EventRepository() {
        super("events");
    }

    public List<EventDocument> find(final EventSearchInvoice invoice) {
        return documents()
                .find()
                .filter(EventDocument.filter(invoice.getFilter()))
                .skip(invoice.getOffset())
                .limit(invoice.getLimit())
                .map(EventDocument::new)
                .into(new ArrayList<>());
    }

    @Override
    public EventDocument findById(final Id id) {
        final DocumentWrapper wrapper = super.findById(id);
        if (wrapper == null) {
            return null;
        }
        return new EventDocument(wrapper.getDocument());
    }
}
