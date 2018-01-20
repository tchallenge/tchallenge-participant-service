package ru.tchallenge.participant.service.domain.event;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import static ru.tchallenge.participant.service.PersistenceManager.collection;

public final class EventRepository {

    public static final EventRepository INSTANCE = new EventRepository();

    public Document findById(final String id) {
        final Document filter = new Document();
        filter.put("_id", new ObjectId(id));
        return find(filter).first();
    }

    public FindIterable<Document> find(final Document filter) {
        return EVENTS.find(filter);
    }

    private final MongoCollection<Document> EVENTS = collection("events");
}
