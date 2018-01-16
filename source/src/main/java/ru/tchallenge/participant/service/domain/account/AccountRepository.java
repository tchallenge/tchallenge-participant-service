package ru.tchallenge.participant.service.domain.account;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.Date;

import static ru.tchallenge.participant.service.PersistenceManager.collection;

public final class AccountRepository {

    public static final AccountRepository INSTANCE = new AccountRepository();

    public Document findByEmail(final String email) {
        final Document filter = new Document();
        filter.put("email", email);
        return find(filter).first();
    }

    public Document findById(final String id) {
        final Document filter = new Document();
        filter.put("_id", new ObjectId(id));
        return find(filter).first();
    }

    public String insert(final Document document) {
        final Instant instant = Instant.now();
        document.put("registeredAt", Date.from(instant));
        document.put("createdAt", Date.from(instant));
        document.put("lastModifiedAt", Date.from(instant));
        ACCOUNTS.insertOne(document);
        return document.getObjectId("_id").toHexString();
    }

    public void update(final Document document) {
        final Document filter = new Document();
        filter.put("_id", document.getObjectId("_id"));
        final Instant instant = Instant.now();
        document.put("lastModifiedAt", Date.from(instant));
        ACCOUNTS.replaceOne(filter, document);
    }

    private FindIterable<Document> find(final Document filter) {
        return ACCOUNTS.find(filter);
    }

    private final MongoCollection<Document> ACCOUNTS = collection("accounts");
}
