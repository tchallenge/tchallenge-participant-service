package ru.tchallenge.participant.service.domain.account;

import lombok.Builder;
import lombok.Data;
import ru.tchallenge.participant.service.utility.validation.ValidationAware;

import java.util.Collection;

@Data
@Builder
public class AccountPasswordUpdateInvoice implements ValidationAware {

    public static boolean validPassword(String password) {
        return password != null && password.length() >= 8;
    }

    private String current;
    private String desired;

    @Override
    public void registerViolations(final Collection<String> violations) {
        if (current == null) {
            violations.add("Current password is missing");
        }
        if (!validPassword(desired)) {
            violations.add("New desired password is invalid");
        }
    }
}
