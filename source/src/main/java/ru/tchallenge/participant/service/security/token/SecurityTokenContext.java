package ru.tchallenge.participant.service.security.token;

public final class SecurityTokenContext {

    public static final SecurityTokenContext INSTANCE = new SecurityTokenContext();

    private final ThreadLocal<SecurityToken> createdTokenContainer = new ThreadLocal<>();

    private SecurityTokenContext() {

    }

    public boolean isNewTokenCreated() {
        return createdTokenContainer.get() != null;
    }

    public SecurityToken getCreatedToken() {
        return createdTokenContainer.get();
    }

    public void setCreatedToken(final SecurityToken createdToken) {
        createdTokenContainer.set(createdToken);
    }
}
