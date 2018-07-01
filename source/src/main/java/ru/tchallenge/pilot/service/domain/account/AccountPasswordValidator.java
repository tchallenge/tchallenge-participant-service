package ru.tchallenge.pilot.service.domain.account;

import ru.tchallenge.pilot.service.context.GenericApplicationComponent;
import ru.tchallenge.pilot.service.context.ManagedComponent;

@ManagedComponent
public class AccountPasswordValidator extends GenericApplicationComponent {

    public void validate(final String password) {
        if (password != null && password.length() < 1) {
            throw new RuntimeException("Password does meet security criteria");
        }
    }
}
