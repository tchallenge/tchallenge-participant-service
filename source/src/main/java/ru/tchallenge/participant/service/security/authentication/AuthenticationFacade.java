package ru.tchallenge.participant.service.security.authentication;

import com.google.common.collect.Sets;
import ru.tchallenge.participant.service.domain.account.Account;
import ru.tchallenge.participant.service.domain.account.AccountManager;
import ru.tchallenge.participant.service.security.token.SecurityToken;
import ru.tchallenge.participant.service.security.token.TokenManager;
import ru.tchallenge.participant.service.security.voucher.SecurityVoucher;
import ru.tchallenge.participant.service.security.voucher.SecurityVoucherManager;
import spark.Request;
import spark.Response;

import java.util.Set;

import static ru.tchallenge.participant.service.utility.serialization.Json.body;

public final class AuthenticationFacade {

    public static void authenticate(final Request request, final Response response) {
        final Authentication authentication;
        if (shouldAuthenticateByInvoice(request)) {
            authentication = authenticateByInvoice(request);
        } else {
            authentication = authenticateByTokenPayloadIfPossible(request);
        }
        AuthenticationContext.setAuthentication(authentication);
    }

    private static boolean shouldAuthenticateByInvoice(final Request request) {
        return request.uri().equals("/security/tokens") && request.requestMethod().equals("POST");
    }

    private static Authentication authenticateByInvoice(final Request request) {
        final AuthenticationInvoice invoice = body(request, AuthenticationInvoice.class);
        if (invoice == null || !invoice.isValid()) {
            throw new RuntimeException("Authentication invoice is invalid");
        }
        final Authentication authentication;
        if (invoice.isByPassword()) {
            authentication = authenticateByPassword(invoice.getEmail(), invoice.getPassword());
        } else if (invoice.isByVoucher()) {
            authentication = authenticateByVoucherPayload(invoice.getVoucherPayload());
        } else {
            throw new RuntimeException("Authentication method is not supported");
        }
        return authentication;
    }

    private static Authentication authenticateByPassword(final String email, final String password) {
        final Account account = AccountManager.retrieveByEmail(email);
        if (!account.getPasswordHash().equals(password)) {
            throw new RuntimeException("Account is password is illegal");
        }
        if (accountIsIllegalForAuthentication(account)) {
            throw new RuntimeException("Account is illegal for authentication");
        }
        return Authentication.builder()
                .accountId(account.getId())
                .accountEmail(account.getEmail())
                .method("password")
                .build();
    }

    private static Authentication authenticateByTokenPayloadIfPossible(final Request request) {
        final String header = request.headers("Authorization");
        if (header == null || header.isEmpty() || !header.startsWith("BEARER ")) {
            return null;
        }
        final String tokenPayload = header.split(" ")[1];
        final SecurityToken token = TokenManager.retrieveByPayload(tokenPayload);
        if (token == null) {
            return null;
        }
        final Account account = AccountManager.retrieveById(token.getAccountId());
        if (accountIsIllegalForAuthentication(account)) {
            return null;
        }
        return Authentication.builder()
                .accountId(account.getId())
                .accountEmail(account.getEmail())
                .tokenPayload(token.getPayload())
                .method("token")
                .build();
    }

    private static Authentication authenticateByVoucherPayload(final String payload) {
        final SecurityVoucher voucher = SecurityVoucherManager.utilizeByPayload(payload);
        if (voucher == null) {
            throw new RuntimeException("Security voucher is expired or does not exist");
        }
        final Account account = AccountManager.retrieveByEmail(voucher.getAccountEmail());
        if (accountIsIllegalForAuthentication(account)) {
            throw new RuntimeException("Account is illegal for authentication");
        }
        return Authentication.builder()
                .accountId(account.getId())
                .accountEmail(account.getEmail())
                .method("voucher")
                .voucherPayload(voucher.getPayload())
                .build();
    }

    private static boolean accountIsIllegalForAuthentication(final Account account) {
        return ILLEGAL_STATUSES.contains(account.getStatus());
    }

    private static final Set<String> ILLEGAL_STATUSES = Sets.newHashSet("SUSPENDED", "BANNED", "DELETED");

    private AuthenticationFacade() {

    }
}
