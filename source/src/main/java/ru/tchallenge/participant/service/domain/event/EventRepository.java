package ru.tchallenge.participant.service.domain.event;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import ru.tchallenge.participant.service.utility.persistence.GenericRepository;

public final class EventRepository extends GenericRepository {

    public static final EventRepository INSTANCE = new EventRepository();

    private EventRepository() {
        super("events");
    }

    public FindIterable<Document> find(final Document filter) {
        return documents().find(filter);
    }
}
