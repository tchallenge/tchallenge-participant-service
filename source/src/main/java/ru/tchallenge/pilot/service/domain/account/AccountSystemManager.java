package ru.tchallenge.pilot.service.domain.account;

import java.sql.Date;
import java.time.Instant;

import org.bson.Document;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.utility.data.DocumentWrapper;
import ru.tchallenge.pilot.service.utility.data.Id;
import ru.tchallenge.pilot.service.utility.data.IdAware;

@ManagedComponent
public class AccountSystemManager extends GenericApplicationComponent {

    private AccountProjector accountProjector;
    private AccountPasswordHashEngine accountPasswordHashEngine;
    private AccountPasswordValidator accountPasswordValidator;
    private AccountRepository accountRepository;

    @Override
    public void init() {
        super.init();
        this.accountProjector = getComponent(AccountProjector.class);
        this.accountPasswordHashEngine = getComponent(AccountPasswordHashEngine.class);
        this.accountPasswordValidator = getComponent(AccountPasswordValidator.class);
        this.accountRepository = getComponent(AccountRepository.class);
    }

    public IdAware create(final AccountInvoice invoice) {
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
        document.put("registeredAt", Date.from(Instant.now()));
        accountRepository.insert(new DocumentWrapper(document));
        return new DocumentWrapper(document).justId();
    }

    public void updatePassword(final Id id, final String password) {
        accountPasswordValidator.validate(password);
        final Document accountDocument = accountRepository.findById(id).getDocument();
        final String passwordHash = accountPasswordHashEngine.hash(password);
        accountDocument.put("passwordHash", passwordHash);
        accountRepository.replace(new DocumentWrapper(accountDocument));
    }

    public Account findByEmail(final String email) {
        final Document document = accountRepository.findByEmail(email);
        if (document == null) {
            return null;
        }
        return accountProjector.intoAccountShort(document);
    }

    public Account findById(final String id) {
        final Document document = accountRepository.findById(new Id(id)).getDocument();
        if (document == null) {
            return null;
        }
        return accountProjector.intoAccountShort(document);
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
}
