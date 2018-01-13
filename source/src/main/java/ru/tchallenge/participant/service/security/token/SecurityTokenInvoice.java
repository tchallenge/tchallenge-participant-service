package ru.tchallenge.participant.service.security.token;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecurityTokenInvoice {

    private String email;
    private String secret;
}
