package ru.tchallenge.pilot.service.domain.account;

import org.bson.Document;

import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.data.GenericRepository;

@ManagedComponent
public class AccountRepository extends GenericRepository {

    public Document findByEmail(final String email) {
        return documents()
                .find()
                .filter(new Document("email", email))
                .first();
    }

    @Override
    protected String getCollectionName() {
        return "accounts";
    }
}
