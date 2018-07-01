package ru.tchallenge.pilot.service.domain.account;

public final class AccountPasswordValidator {

    public static final AccountPasswordValidator INSTANCE = new AccountPasswordValidator();

    public void validate(final String password) {
        if (password != null && password.length() < 1) {
            throw new RuntimeException("Password does meet security criteria");
        }
    }

    private AccountPasswordValidator() {

    }
}
