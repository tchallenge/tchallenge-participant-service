package ru.tchallenge.pilot.service.domain.event;

import java.util.ArrayList;
import java.util.List;

import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.data.DocumentWrapper;
import ru.tchallenge.pilot.service.utility.data.GenericRepository;
import ru.tchallenge.pilot.service.utility.data.Id;

@ManagedComponent
public class EventRepository extends GenericRepository {

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

    @Override
    protected String getCollectionName() {
        return "events";
    }
}
