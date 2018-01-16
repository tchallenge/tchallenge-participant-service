package ru.tchallenge.participant.service.domain.account;

import org.bson.Document;
import ru.tchallenge.participant.service.security.authentication.AuthenticationContext;

public final class AccountManager {

    public static final AccountManager INSTANCE = new AccountManager();

    public Account retrieveCurrent() {
        final String id = AuthenticationContext.getAuthentication().getAccountId();
        final Document accountDocument = accountRepository.findById(id);
        return accountMapper.intoAccount(accountDocument);
    }

    public void updateCurrentPassword(final AccountPasswordUpdateInvoice invoice) {
        final String id = AuthenticationContext.getAuthentication().getAccountId();
        accountPasswordValidator.validate(invoice.getDesired());
        final Document accountDocument = accountRepository.findById(id);
        final String passwordHash = accountDocument.getString("passwordHash");
        if (!accountPasswordHashEngine.match(invoice.getCurrent(), passwordHash)) {
            throw new RuntimeException("Current password does not match");
        }
        accountDocument.put("passwordHash", accountPasswordHashEngine.hash(invoice.getDesired()));
        accountRepository.update(accountDocument);
    }

    public void updateCurrentPersonality(final AccountPersonality invoice) {
        final String id = AuthenticationContext.getAuthentication().getAccountId();
        final Document accountDocument = accountRepository.findById(id);
        final Document accountPersonalityDocument = accountSystemManager.createAccountPersonalityDocument(invoice);
        accountDocument.put("personality", accountPersonalityDocument);
        accountRepository.update(accountDocument);
    }

    public void updateCurrentStatus(final AccountStatusUpdateInvoice invoice) {
        final String id = AuthenticationContext.getAuthentication().getAccountId();
        final Document accountDocument = accountRepository.findById(id);
        if (!invoice.getNewStatus().equals("DELETED")) {
            throw new RuntimeException("Status is not permitted");
        }
        accountDocument.put("status", invoice.getNewStatus());
        accountRepository.update(accountDocument);
    }

    private final AccountPasswordHashEngine accountPasswordHashEngine = AccountPasswordHashEngine.INSTANCE;
    private final AccountPasswordValidator accountPasswordValidator = AccountPasswordValidator.INSTANCE;
    private final AccountMapper accountMapper = AccountMapper.INSTANCE;
    private final AccountRepository accountRepository = AccountRepository.INSTANCE;
    private final AccountSystemManager accountSystemManager = AccountSystemManager.INSTANCE;

    private AccountManager() {

    }
}
