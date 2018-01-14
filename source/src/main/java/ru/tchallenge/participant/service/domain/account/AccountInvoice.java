package ru.tchallenge.participant.service.domain.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountInvoice {

    private String email;
    private String password;
    private AccountPersonality personality;
}
