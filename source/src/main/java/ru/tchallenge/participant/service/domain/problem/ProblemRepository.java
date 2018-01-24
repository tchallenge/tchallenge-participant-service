package ru.tchallenge.participant.service.domain.problem;

import com.google.common.collect.Lists;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;

import static ru.tchallenge.participant.service.PersistenceManager.collection;

public final class ProblemRepository {

    public static final ProblemRepository INSTANCE = new ProblemRepository();

    public AggregateIterable<Document> findRandom(final ProblemRandomInvoice invoice) {
        final Bson match = Aggregates.match(new Document().append("categories", invoice.getCategories()).append("difficulty", "HARD"));
        final Bson sample = Aggregates.sample(invoice.getLimit());
        return problemCollection.aggregate(Lists.newArrayList(match, sample));
    }

    private final MongoCollection<Document> problemCollection = collection("problems");
}
