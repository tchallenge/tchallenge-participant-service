package ru.tchallenge.pilot.service.security.token;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ru.tchallenge.pilot.service.domain.account.Account;
import ru.tchallenge.pilot.service.domain.account.AccountSystemManager;

public final class TokenManager {

    public static final TokenManager INSTANCE = new TokenManager();

    private final AccountSystemManager accountSystemManager = AccountSystemManager.INSTANCE;
    private final SecurityTokenContext securityTokenContext = SecurityTokenContext.INSTANCE;
    private final Map<String, SecurityToken> tokens = new HashMap<>();
    private final Duration tokenExpirationPeriod = Duration.ofHours(1);

    private TokenManager() {

    }

    public SecurityToken create(final String accountId) {
        final SecurityToken token = createNewToken(accountId);
        tokens.put(token.getPayload(), token);
        securityTokenContext.setCreatedToken(token);
        return token;
    }

    public SecurityToken retrieveByPayload(final String payload) {
        String predefinedTokenEnabled = System.getenv("TCHALLENGE_SECURITY_TOKEN_PREDEFINED_ENABLED");
        if (predefinedTokenEnabled.equalsIgnoreCase("true") && payload.equals("PREDEFINED")) {
            final Account account = accountSystemManager.findByEmail("test.user1@example.com");
            if (account == null) {
                throw new UnsupportedOperationException("No predefined account");
            }
            return createNewToken(account.getId().toHex());
        }
        final SecurityToken token = tokens.get(payload);
        if (token == null || token.isExpired()) {
            tokens.remove(payload);
            return null;
        }
        token.prolongate(tokenExpirationPeriod);
        return token;
    }

    public void deleteByPayload(final String payload) {
        tokens.remove(payload);
    }

    private SecurityToken createNewToken(final String accountId) {
        return SecurityToken.builder()
                .id(UUID.randomUUID().toString())
                .payload(UUID.randomUUID().toString())
                .accountId(accountId)
                .createdAt(Instant.now())
                .validUntil(Instant.now().plus(tokenExpirationPeriod))
                .build();
    }
}
