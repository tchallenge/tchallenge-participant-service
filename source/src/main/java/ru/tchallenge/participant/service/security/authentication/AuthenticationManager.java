package ru.tchallenge.participant.service.security.authentication;

import java.util.Set;

import com.google.common.collect.Sets;
import org.bson.Document;

import ru.tchallenge.participant.service.domain.account.Account;
import ru.tchallenge.participant.service.domain.account.AccountPasswordHashEngine;
import ru.tchallenge.participant.service.domain.account.AccountRepository;
import ru.tchallenge.participant.service.domain.account.AccountSystemManager;
import ru.tchallenge.participant.service.security.token.SecurityToken;
import ru.tchallenge.participant.service.security.token.TokenManager;
import ru.tchallenge.participant.service.security.voucher.SecurityVoucher;
import ru.tchallenge.participant.service.security.voucher.SecurityVoucherManager;

public final class AuthenticationManager {

    public static final AuthenticationManager INSTANCE = new AuthenticationManager();

    private final AccountPasswordHashEngine accountPasswordHashEngine = AccountPasswordHashEngine.INSTANCE;
    private final AccountRepository accountRepository = AccountRepository.INSTANCE;
    private final AccountSystemManager accountSystemManager = AccountSystemManager.INSTANCE;
    private final Set<String> illegalStatuses = Sets.newHashSet("SUSPENDED", "BANNED", "DELETED");
    private final SecurityVoucherManager securityVoucherManager = SecurityVoucherManager.INSTANCE;
    private final TokenManager tokenManager = TokenManager.INSTANCE;

    public Authentication authenticateByPassword(final AuthenticationInvoice invoice) {
        final String email = invoice.getEmail();
        final Document accountDocument = accountRepository.findByEmail(email);
        if (accountDocument == null) {
            throw accountIsMissingOrPasswordIsIncorrect();
        }
        final String password = invoice.getPassword();
        if (!accountPasswordHashEngine.match(password, accountDocument.getString("passwordHash"))) {
            throw accountIsMissingOrPasswordIsIncorrect();
        }
        final Account account = accountSystemManager.findByEmail(email);
        if (accountIsIllegalForAuthentication(account)) {
            throw accountHasIllegalStatus();
        }
        return Authentication.builder()
                .accountId(account.getId().toHex())
                .accountEmail(account.getEmail())
                .method(AuthenticationMethod.PASSWORD)
                .build();
    }

    public Authentication authenticateByToken(final String tokenPayload) {
        final SecurityToken token = tokenManager.retrieveByPayload(tokenPayload);
        if (token == null) {
            throw tokenIsExpiredOrMissing();
        }
        final Account account = accountSystemManager.findById(token.getAccountId());
        if (account == null) {
            throw accountIsMissing();
        }
        if (accountIsIllegalForAuthentication(account)) {
            throw accountHasIllegalStatus();
        }
        return Authentication.builder()
                .accountId(account.getId().toHex())
                .accountEmail(account.getEmail())
                .tokenPayload(token.getPayload())
                .method(AuthenticationMethod.TOKEN)
                .build();
    }

    public Authentication authenticateByVoucher(final AuthenticationInvoice invoice) {
        final String payload = invoice.getVoucherPayload();
        final SecurityVoucher voucher = securityVoucherManager.utilizeByPayload(payload);
        if (voucher == null) {
            throw voucherIsExpiredOrMissing();
        }
        final Account account = accountSystemManager.findByEmail(voucher.getAccountEmail());
        if (account == null) {
            throw accountIsMissing();
        }
        if (accountIsIllegalForAuthentication(account)) {
            throw accountHasIllegalStatus();
        }
        if (invoice.isPasswordUpdateRequested()) {
            accountSystemManager.updatePassword(account.getId(), invoice.getPasswordUpdate());
        }
        return Authentication.builder()
                .accountId(account.getId().toHex())
                .accountEmail(account.getEmail())
                .method(AuthenticationMethod.VOUCHER)
                .voucherPayload(voucher.getPayload())
                .build();
    }

    private boolean accountIsIllegalForAuthentication(final Account account) {
        return illegalStatuses.contains(account.getStatus());
    }

    private RuntimeException accountIsMissing() {
        return new RuntimeException("Account is missing");
    }

    private RuntimeException accountIsMissingOrPasswordIsIncorrect() {
        return new RuntimeException("Account is missing or password is incorrect");
    }

    private RuntimeException accountHasIllegalStatus() {
        return new RuntimeException("Account cannot be accessed due to its status");
    }

    private RuntimeException tokenIsExpiredOrMissing() {
        return new RuntimeException("Security token is expired or does not exist");
    }

    private RuntimeException voucherIsExpiredOrMissing() {
        return new RuntimeException("Security voucher is expired or does not exist");
    }
}
