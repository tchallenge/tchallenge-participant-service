package ru.tchallenge.pilot.service.security.token;

import spark.Request;

import ru.tchallenge.pilot.service.security.authentication.AuthenticationRequestContext;

public final class TokenFacade {

    public static final TokenFacade INSTANCE = new TokenFacade();

    private final TokenManager tokenManager = TokenManager.INSTANCE;

    private TokenFacade() {

    }

    public SecurityToken createForCurrentAccount(Request request) {
        return tokenManager.create(getCurrentAccountId(request));
    }

    public SecurityToken retrieveCurrent(Request request) {
        return tokenManager.retrieveByPayload(getCurrentTokenPayload(request));
    }

    public void deleteCurrent(Request request) {
        tokenManager.deleteByPayload(getCurrentTokenPayload(request));
    }

    private String getCurrentAccountId(Request request) {
        return new AuthenticationRequestContext(request).getAuthentication().getAccountId();
    }

    private String getCurrentTokenPayload(Request request) {
        return new AuthenticationRequestContext(request).getAuthentication().getTokenPayload();
    }
}
