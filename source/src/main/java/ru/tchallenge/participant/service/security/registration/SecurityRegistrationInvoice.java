package ru.tchallenge.participant.service.security.registration;

import lombok.Data;

@Data
public final class SecurityRegistrationInvoice {

    private String backlink;
    private String email;
    private String password;
    private String quickname;

    public boolean isValid() {
        return email != null && !email.isEmpty() && backlink != null && !backlink.isEmpty() && password != null;
    }
}
