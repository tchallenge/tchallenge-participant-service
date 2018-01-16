package ru.tchallenge.participant.service.domain.account;

import org.bson.Document;

public final class AccountSystemManager {

    public static final AccountSystemManager INSTANCE = new AccountSystemManager();

    public String create(final AccountInvoice invoice) {
        accountPasswordValidator.validate(invoice.getPassword());
        if (accountRepository.findByEmail(invoice.getEmail()) != null) {
            throw new RuntimeException("Account with such email already exists");
        }
        final Document document = new Document();
        document.put("email", invoice.getEmail());
        document.put("passwordHash", accountPasswordHashEngine.hash(invoice.getPassword()));
        document.put("status", "APPROVED");
        final Document personalityDocument = createAccountPersonalityDocument(invoice.getPersonality());
        document.put("personality", personalityDocument);
        return accountRepository.insert(document);
    }

    public Account findByEmail(final String email) {
        final Document document = accountRepository.findByEmail(email);
        if (document == null) {
            return null;
        }
        return accountMapper.intoAccountShort(document);
    }

    public Account findById(final String id) {
        final Document document = accountRepository.findById(id);
        if (document == null) {
            return null;
        }
        return accountMapper.intoAccountShort(document);
    }

    public Document createAccountPersonalityDocument(final AccountPersonality accountPersonality) {
        final Document result = new Document();
        if (accountPersonality != null) {
            result.put("firstname", accountPersonality.getFirstname());
            result.put("lastname", accountPersonality.getLastname());
            result.put("middlename", accountPersonality.getMiddlename());
            result.put("quickname", accountPersonality.getQuickname());
            result.put("essay", accountPersonality.getEssay());
            result.put("linkedin", accountPersonality.getLinkedin());
            result.put("hh", accountPersonality.getHh());
            result.put("github", accountPersonality.getGithub());
            result.put("bitbucket", accountPersonality.getBitbucket());
            result.put("website", accountPersonality.getWebsite());
        }
        return result;
    }

    private final AccountMapper accountMapper = AccountMapper.INSTANCE;
    private final AccountPasswordHashEngine accountPasswordHashEngine = AccountPasswordHashEngine.INSTANCE;
    private final AccountPasswordValidator accountPasswordValidator = AccountPasswordValidator.INSTANCE;
    private final AccountRepository accountRepository = AccountRepository.INSTANCE;

    private AccountSystemManager() {

    }
}
