package ru.tchallenge.participant.service.domain.account;

import org.bson.Document;

import ru.tchallenge.participant.service.security.authentication.AuthenticationContext;
import ru.tchallenge.participant.service.utility.data.DocumentWrapper;
import ru.tchallenge.participant.service.utility.data.Id;

public final class AccountManager {

    public static final AccountManager INSTANCE = new AccountManager();

    private final AccountPasswordHashEngine accountPasswordHashEngine = AccountPasswordHashEngine.INSTANCE;
    private final AccountPasswordValidator accountPasswordValidator = AccountPasswordValidator.INSTANCE;
    private final AccountProjector accountProjector = AccountProjector.INSTANCE;
    private final AccountRepository accountRepository = AccountRepository.INSTANCE;
    private final AccountSystemManager accountSystemManager = AccountSystemManager.INSTANCE;
    private final AuthenticationContext authenticationContext = AuthenticationContext.INSTANCE;

    private AccountManager() {

    }

    public Account retrieveCurrent() {
        final String id = authenticatedAccountId();
        final Document accountDocument = accountRepository.findById(new Id(id)).getDocument();
        return accountProjector.intoAccount(accountDocument);
    }

    public void updateCurrentPassword(final AccountPasswordUpdateInvoice invoice) {
        final String id = authenticatedAccountId();
        accountPasswordValidator.validate(invoice.getDesired());
        final Document accountDocument = accountRepository.findById(new Id(id)).getDocument();
        final String passwordHash = accountDocument.getString("passwordHash");
        if (!accountPasswordHashEngine.match(invoice.getCurrent(), passwordHash)) {
            throw new RuntimeException("Current password does not match");
        }
        accountDocument.put("passwordHash", accountPasswordHashEngine.hash(invoice.getDesired()));
        accountRepository.replace(new DocumentWrapper(accountDocument));
    }

    public void updateCurrentPersonality(final AccountPersonality invoice) {
        final String id = authenticatedAccountId();
        final Document accountDocument = accountRepository.findById(new Id(id)).getDocument();
        final Document accountPersonalityDocument = accountSystemManager.createAccountPersonalityDocument(invoice);
        accountDocument.put("personality", accountPersonalityDocument);
        accountRepository.replace(new DocumentWrapper(accountDocument));
    }

    public void updateCurrentStatus(final AccountStatusUpdateInvoice invoice) {
        final String id = authenticatedAccountId();
        final Document accountDocument = accountRepository.findById(new Id(id)).getDocument();
        if (!invoice.getNewStatus().equals("DELETED")) {
            throw new RuntimeException("Status is not permitted");
        }
        accountDocument.put("status", invoice.getNewStatus());
        accountRepository.replace(new DocumentWrapper(accountDocument));
    }

    private String authenticatedAccountId() {
        return authenticationContext.getAuthentication().getAccountId();
    }
}
