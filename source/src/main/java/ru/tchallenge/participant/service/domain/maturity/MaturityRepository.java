package ru.tchallenge.participant.service.domain.maturity;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import ru.tchallenge.participant.service.utility.persistence.GenericRepository;

import static com.mongodb.client.model.Filters.in;

public final class MaturityRepository extends GenericRepository {

    public static final MaturityRepository INSTANCE = new MaturityRepository();

    private MaturityRepository() {
        super("maturities");
    }
    public FindIterable<Document> findByTextcodes(final String... textcodes) {
        return documents()
                .find()
                .filter(in("textcode", textcodes));
    }
}
