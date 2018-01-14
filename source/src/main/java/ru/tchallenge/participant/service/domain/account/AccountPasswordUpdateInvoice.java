package ru.tchallenge.participant.service.domain.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountPasswordUpdateInvoice {

    public static boolean validPassword(String password) {
        return password != null && password.length() >= 8;
    }

    private String current;
    private String desired;

    public boolean isValid() {
        return current != null && validPassword(desired) && !current.equals(desired);
    }
}
