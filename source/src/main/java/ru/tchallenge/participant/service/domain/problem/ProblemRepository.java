package ru.tchallenge.participant.service.domain.problem;

import com.google.common.collect.Lists;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;
import ru.tchallenge.participant.service.utility.persistence.GenericRepository;

public final class ProblemRepository extends GenericRepository {

    public static final ProblemRepository INSTANCE = new ProblemRepository();

    private ProblemRepository() {
        super("problems");
    }

    public AggregateIterable<Document> findRandom(final ProblemRandomInvoice invoice) {
        final Bson match = Aggregates.match(new Document().append("categories", invoice.getCategories()).append("difficulty", "HARD"));
        final Bson sample = Aggregates.sample(invoice.getLimit());
        return documents().aggregate(Lists.newArrayList(match, sample));
    }
}
