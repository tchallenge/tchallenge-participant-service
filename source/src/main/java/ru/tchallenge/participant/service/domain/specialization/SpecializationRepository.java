package ru.tchallenge.participant.service.domain.specialization;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import ru.tchallenge.participant.service.utility.persistence.GenericRepository;

import static com.mongodb.client.model.Filters.in;

public final class SpecializationRepository extends GenericRepository {

    public static final SpecializationRepository INSTANCE = new SpecializationRepository();

    private SpecializationRepository() {
        super("specializations");
    }

    public FindIterable<Document> findByTextcodes(final String... textcodes) {
        return documents()
                .find()
                .filter(in("textcode", textcodes));
    }
}
