package ru.tchallenge.pilot.service.domain.account;

import org.mindrot.jbcrypt.BCrypt;

public final class AccountPasswordHashEngine {

    public static final AccountPasswordHashEngine INSTANCE = new AccountPasswordHashEngine();

    public String hash(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean match(final String password, final String passwordHash) {
        return BCrypt.checkpw(password, passwordHash);
    }

    private AccountPasswordHashEngine() {

    }
}
