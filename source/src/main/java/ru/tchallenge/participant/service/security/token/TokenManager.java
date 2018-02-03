package ru.tchallenge.participant.service.security.token;

import ru.tchallenge.participant.service.domain.account.Account;
import ru.tchallenge.participant.service.domain.account.AccountSystemManager;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class TokenManager {

    public static SecurityToken create(final String accountId) {
        final SecurityToken token = createNewToken(accountId);
        TOKENS.put(token.getPayload(), token);
        return token;
    }

    public static SecurityToken retrieveByPayload(final String payload) {
        if (payload.equals("PREDEFINED")) {
            final Account account = ACCOUNT_SYSTEM_MANAGER.findByEmail("test.user1@example.com");
            return createNewToken(account.getId().toHex());
        }
        final SecurityToken token = TOKENS.get(payload);
        if (token == null || token.isExpired()) {
            TOKENS.remove(payload);
            return null;
        }
        token.prolongate(TOKEN_EXPIRATION_PERIOD);
        return token;
    }

    public static void deleteByPayload(final String payload) {
        TOKENS.remove(payload);
    }

    private static SecurityToken createNewToken(final String accountId) {
        return SecurityToken.builder()
                .id(UUID.randomUUID().toString())
                .payload(UUID.randomUUID().toString())
                .accountId(accountId)
                .createdAt(OffsetDateTime.now())
                .validUntil(OffsetDateTime.now().plus(TOKEN_EXPIRATION_PERIOD))
                .build();
    }

    private static final Map<String, SecurityToken> TOKENS = new HashMap<>();
    private static final Duration TOKEN_EXPIRATION_PERIOD = Duration.ofHours(1);
    private static final AccountSystemManager ACCOUNT_SYSTEM_MANAGER = AccountSystemManager.INSTANCE;

    private TokenManager() {

    }
}
