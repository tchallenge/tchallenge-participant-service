package ru.tchallenge.participant.service.domain.account;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import static ru.tchallenge.participant.service.PersistenceManager.collection;

public final class AccountManager {

    public static Account create(AccountInvoice invoice) {
        throw new UnsupportedOperationException();
    }

    public static Account retrieveById(String id) {
        Document filter = new Document();
        filter.put("_id", new ObjectId(id));
        return ACCOUNTS.find(filter).map(AccountManager::mapIntoAccount).first();
    }

    public static Account retrieveByEmailAndPassword(String email, String password) {
        Document filter = new Document();
        filter.put("email", email);
        filter.put("passwordHash", password);
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
