package ru.tchallenge.participant.service.security.token;

import ru.tchallenge.participant.service.security.authentication.AuthenticationContext;
import ru.tchallenge.participant.service.security.authentication.AuthenticationContextBean;

public final class TokenFacade {

    public static final TokenFacade INSTANCE = new TokenFacade();

    private final TokenManager tokenManager = TokenManager.INSTANCE;
    private final AuthenticationContext authenticationContext = AuthenticationContextBean.INSTANCE;

    private TokenFacade() {

    }

    public SecurityToken createForCurrentAccount() {
        return tokenManager.create(getCurrentAccountId());
    }

    public SecurityToken retrieveCurrent() {
        return tokenManager.retrieveByPayload(getCurrentTokenPayload());
    }

    public void deleteCurrent() {
        tokenManager.deleteByPayload(getCurrentTokenPayload());
    }

    private String getCurrentAccountId() {
        return authenticationContext.getAuthentication().getAccountId();
    }

    private String getCurrentTokenPayload() {
        return authenticationContext.getAuthentication().getTokenPayload();
    }
}
