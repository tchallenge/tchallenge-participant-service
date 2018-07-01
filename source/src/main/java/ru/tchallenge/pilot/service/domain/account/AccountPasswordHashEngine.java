package ru.tchallenge.pilot.service.domain.account;

import org.mindrot.jbcrypt.BCrypt;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;

@ManagedComponent
public class AccountPasswordHashEngine extends GenericApplicationComponent {

    public String hash(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean match(final String password, final String passwordHash) {
        return BCrypt.checkpw(password, passwordHash);
    }
}
