package ru.tchallenge.participant.service.domain.account;

public final class AccountPasswordValidator {

    public static final AccountPasswordValidator INSTANCE = new AccountPasswordValidator();

    public void validate(final String password) {
        if (password != null && password.length() < 8) {
            throw new RuntimeException("Password does meet security criteria");
        }
    }

    private AccountPasswordValidator() {

    }
}
