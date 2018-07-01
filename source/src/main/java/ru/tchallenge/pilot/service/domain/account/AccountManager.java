package ru.tchallenge.pilot.service.domain.account;

import spark.Request;

import org.bson.Document;

import ru.tchallenge.pilot.service.security.authentication.AuthenticationRequestContext;
import ru.tchallenge.pilot.service.utility.data.DocumentWrapper;
import ru.tchallenge.pilot.service.utility.data.Id;

public final class AccountManager {

    public static final AccountManager INSTANCE = new AccountManager();

    private final AccountPasswordHashEngine accountPasswordHashEngine = AccountPasswordHashEngine.INSTANCE;
    private final AccountPasswordValidator accountPasswordValidator = AccountPasswordValidator.INSTANCE;
    private final AccountProjector accountProjector = AccountProjector.INSTANCE;
    private final AccountRepository accountRepository = AccountRepository.INSTANCE;
    private final AccountSystemManager accountSystemManager = AccountSystemManager.INSTANCE;

    private AccountManager() {

    }

    public Account retrieveCurrent(Request request) {
        final String id = authenticatedAccountId(request);
        final Document accountDocument = accountRepository.findById(new Id(id)).getDocument();
        return accountProjector.intoAccount(accountDocument);
    }

    public void updateCurrentPassword(Request request, final AccountPasswordUpdateInvoice invoice) {
        final String id = authenticatedAccountId(request);
        accountPasswordValidator.validate(invoice.getDesired());
        final Document accountDocument = accountRepository.findById(new Id(id)).getDocument();
        final String passwordHash = accountDocument.getString("passwordHash");
        if (!accountPasswordHashEngine.match(invoice.getCurrent(), passwordHash)) {
            throw new RuntimeException("Current password does not match");
        }
        accountDocument.put("passwordHash", accountPasswordHashEngine.hash(invoice.getDesired()));
        accountRepository.replace(new DocumentWrapper(accountDocument));
    }

    public void updateCurrentPersonality(Request request, final AccountPersonality invoice) {
        final String id = authenticatedAccountId(request);
        final Document accountDocument = accountRepository.findById(new Id(id)).getDocument();
        final Document accountPersonalityDocument = accountSystemManager.createAccountPersonalityDocument(invoice);
        accountDocument.put("personality", accountPersonalityDocument);
        accountRepository.replace(new DocumentWrapper(accountDocument));
    }

    public void updateCurrentStatus(Request request, final AccountStatusUpdateInvoice invoice) {
        final String id = authenticatedAccountId(request);
        final Document accountDocument = accountRepository.findById(new Id(id)).getDocument();
        if (!invoice.getNewStatus().equals("DELETED")) {
            throw new RuntimeException("Status is not permitted");
        }
        accountDocument.put("status", invoice.getNewStatus());
        accountRepository.replace(new DocumentWrapper(accountDocument));
    }

    private String authenticatedAccountId(Request request) {
        return new AuthenticationRequestContext(request).getAuthentication().getAccountId();
    }
}
