package ru.tchallenge.participant.service.domain.maturity;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import static com.mongodb.client.model.Filters.in;
import static ru.tchallenge.participant.service.PersistenceManager.collection;

public final class MaturityRepository {

    public static final MaturityRepository INSTANCE = new MaturityRepository();

    public FindIterable<Document> findByTextcodes(final String... textcodes) {
        return documents
                .find()
                .filter(in("textcode", textcodes));
    }

    private final MongoCollection<Document> documents = collection("maturities");

    private MaturityRepository() {

    }
}
