package ru.tchallenge.pilot.service.security.token;

import spark.Request;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;
import ru.tchallenge.pilot.service.security.authentication.AuthenticationRequestContext;

@ManagedComponent
public class TokenFacade extends GenericApplicationComponent {

    private TokenManager tokenManager;

    @Override
    public void init() {
        super.init();
        this.tokenManager = getComponent(TokenManager.class);
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
