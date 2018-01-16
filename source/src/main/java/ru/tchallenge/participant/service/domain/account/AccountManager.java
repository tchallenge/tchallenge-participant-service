package ru.tchallenge.participant.service.domain.account;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import static ru.tchallenge.participant.service.PersistenceManager.collection;

public final class AccountManager {

    public static Account create(AccountInvoice invoice) {
        final Document filter = new Document();
        filter.put("email", invoice.getEmail());
        if (ACCOUNTS.count(filter) > 0) {
            throw new RuntimeException("Account with specified email already exists");
        }
        final Document accountDocument = new Document();
        accountDocument.put("email", invoice.getEmail());
        accountDocument.put("passwordHash", invoice.getPassword());
        accountDocument.put("personality", createAccountPersonalityDocument(invoice.getPersonality()));
        ACCOUNTS.insertOne(accountDocument);
        return ACCOUNTS.find(filter).map(AccountManager::mapIntoAccount).first();
    }

    private static Document createAccountPersonalityDocument(final AccountPersonality accountPersonality) {
        final Document result = new Document();
        if (accountPersonality != null) {
            result.put("quickname", accountPersonality.getQuickname());
        }
        return result;
    }

    public static Account retrieveById(String id) {
        Document filter = new Document();
        filter.put("_id", new ObjectId(id));
        return ACCOUNTS.find(filter).map(AccountManager::mapIntoAccount).first();
    }

    public static Account retrieveByEmail(final String email) {
        Document filter = new Document();
        filter.put("email", email);
        return ACCOUNTS.find(filter).map(AccountManager::mapIntoAccount).first();
    }

    public static Account updatePassword(AccountPasswordUpdateInvoice invoice) {
        throw new UnsupportedOperationException();
    }

    public static Account updatePersonality(AccountPersonality invoice) {
        throw new UnsupportedOperationException();
    }

    public static Account updateStatus(AccountStatusUpdateInvoice invoice) {
        throw new UnsupportedOperationException();
    }

    private static Account mapIntoAccount(Document document) {
        return Account.builder()
                .id(document.getObjectId("_id").toHexString())
                .status(document.getString("status"))
                .email(document.getString("email"))
                .personality(mapIntoAccountPersonality((Document) document.get("personality")))
                .build();
    }

    private static AccountPersonality mapIntoAccountPersonality(Document document) {
        return AccountPersonality.builder()
                .quickname(document.getString("quickname"))
                .build();
    }

    private static final MongoCollection<Document> ACCOUNTS = collection("accounts");

    private AccountManager() {

    }
}
